package com.example.clickservice.repository.impl

import com.example.clickservice.controller.model.ClickHistogramResponse
import com.example.clickservice.enums.TimeInterval
import com.example.clickservice.repository.entity.ClickHistogram
import com.example.clickservice.repository.extra.ClickHistogramRepositoryExtra
import com.example.clickservice.service.model.request.ClickCountRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.aggregation.DateOperators.dateFromString
import org.springframework.data.mongodb.core.aggregation.DateOperators.dateOf
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import java.text.DateFormat
import java.text.SimpleDateFormat


@Repository
class ClickHistogramRepositoryImpl(
    val mongoTemplate: MongoTemplate
) : ClickHistogramRepositoryExtra {

    companion object {
        val DATE_FORMAT: DateFormat = SimpleDateFormat("yyyy/MM/dd HH")

        private val DAY_PROJECTION_FIRST_STAGE = Aggregation.project("_id")
            .and(dateOf(ClickHistogram.DATE).toString("%Y-%m-%d")).`as`(ClickHistogram.DATE)
            .and(ClickHistogram.COUNT).`as`(ClickHistogram.COUNT)


        private val DAY_PROJECTION_SECOND_STAGE = Aggregation.project(ClickHistogram.DATE)
            .and(dateFromString("\$_id").withFormat("%Y-%m-%d")).`as`(ClickHistogram.DATE)
            .and(ClickHistogram.COUNT).`as`(ClickHistogram.COUNT)

        private val SORT_STAGE = Aggregation.sort(
            Sort.by(
                Sort.Direction.DESC,
                ClickHistogram.DATE
            )
        )

        private val DAY_GROUP_STAGE = Aggregation.group(
            ClickHistogram.DATE
        ).sum(ClickHistogram.COUNT).`as`(ClickHistogram.COUNT)
    }

    override fun bulkAddition(data: Map<String, Long>) {
        val bulk: BulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED, ClickHistogram::class.java)
        for (key in data.keys) {
            val update = Update().inc(ClickHistogram.COUNT, data[key]!!)
            val query = Query(
                Criteria.where(ClickHistogram.DATE).`is`(
                    DATE_FORMAT.parse(key)
                )
            )
            bulk.upsert(query, update)
        }
        bulk.execute()
    }

    override fun countClicks(timeInterval: TimeInterval, body: ClickCountRequest?): List<ClickHistogramResponse> {
        val pipeline = mutableListOf<AggregationOperation>()

        body?.let {
            pipeline.add(
                Aggregation.match(
                    Criteria().andOperator(Criteria.where(ClickHistogram.DATE).gte(body.start).lte(body.end))
                )
            )
        }

        if(timeInterval == TimeInterval.DAY)
            pipeline.addAll(mutableListOf(
                DAY_PROJECTION_FIRST_STAGE,
                DAY_GROUP_STAGE,
                DAY_PROJECTION_SECOND_STAGE
                ))

        pipeline.add(SORT_STAGE)

        val aggregation = Aggregation.newAggregation(
            pipeline
        )

        return mongoTemplate.aggregate(
            aggregation,
            ClickHistogram.COLLECTION_NAME,
            ClickHistogramResponse::class.java
        ).mappedResults

    }

}