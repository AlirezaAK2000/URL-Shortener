package com.example.clickservice.repository.impl

import com.example.clickservice.enums.TimeInterval
import com.example.clickservice.repository.entity.Click
import com.example.clickservice.repository.extra.ClickRepositoryExtra
import com.example.clickservice.service.model.aggregation.id.ClickTimeIntervalPopulationId
import com.example.clickservice.service.model.aggregation.population.ClickPopulation
import com.example.clickservice.service.model.aggregation.population.ClickTimeIntervalPopulation
import com.example.clickservice.service.model.request.ClickCountRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.stereotype.Repository
import java.util.*


@Repository
class ClickRepositoryImpl(
    val mongoTemplate: MongoTemplate
) : ClickRepositoryExtra {

    companion object {
        private const val YEAR_OPERATOR = "year"
        private const val DAYCARE_OPERATOR: String = "dayOfYear"
        private const val HOUR_OPERATOR = "hour"

        val HOUR_PROJECTION_STAGE = Aggregation.project(Click.CLICK_TIME)
            .andExpression("$YEAR_OPERATOR(${Click.CLICK_TIME})").`as`(ClickTimeIntervalPopulationId.YEAR)
            .andExpression("$DAYCARE_OPERATOR(${Click.CLICK_TIME})").`as`(ClickTimeIntervalPopulationId.DAY)
            .andExpression(("$HOUR_OPERATOR(${Click.CLICK_TIME})")).`as`(ClickTimeIntervalPopulationId.HOUR)

        val DAY_PROJECTION_STAGE = Aggregation.project(Click.CLICK_TIME)
            .andExpression("$YEAR_OPERATOR(${Click.CLICK_TIME})").`as`(ClickTimeIntervalPopulationId.YEAR)
            .andExpression("$DAYCARE_OPERATOR(${Click.CLICK_TIME})").`as`(ClickTimeIntervalPopulationId.DAY)

        val HOUR_SORT_STAGE = Aggregation.sort(
            Sort.by(
                Sort.Direction.DESC,
                "_id.${ClickTimeIntervalPopulationId.YEAR}",
                "_id.${ClickTimeIntervalPopulationId.DAY}",
                "_id.${ClickTimeIntervalPopulationId.HOUR}"
            )
        )

        val DAY_SORT_STAGE = Aggregation.sort(
            Sort.by(
                Sort.Direction.DESC,
                "_id.${ClickTimeIntervalPopulationId.YEAR}",
                "_id.${ClickTimeIntervalPopulationId.DAY}"
            )
        )

        val HOUR_GROUP_STAGE = Aggregation.group(
            ClickTimeIntervalPopulationId.YEAR,
            ClickTimeIntervalPopulationId.DAY,
            ClickTimeIntervalPopulationId.HOUR
        ).count().`as`(ClickTimeIntervalPopulation.COUNT)

        val DAY_GROUP_STAGE = Aggregation.group(
            ClickTimeIntervalPopulationId.YEAR,
            ClickTimeIntervalPopulationId.DAY
        ).count().`as`(ClickTimeIntervalPopulation.COUNT)
    }

    override fun findAllURLClickCountByDate(
        body: ClickCountRequest?
    ): AggregationResults<ClickPopulation> {

        val pipeline = arrayListOf<AggregationOperation>()

        body?.let {
            pipeline.add(
                Aggregation.match(
                    Criteria().andOperator(
                        Criteria.where(Click.CLICK_TIME).gte(body.start).lte(body.end)
                    )
                )
            )
        }

        pipeline.add(Aggregation.group(Click.URL_ID).count().`as`(ClickPopulation.COUNT))

        pipeline.add(Aggregation.sort(Sort.by(Sort.Direction.DESC, ClickPopulation.COUNT)))

        val aggregation = Aggregation.newAggregation(
            pipeline
        )

        return mongoTemplate.aggregate(
            aggregation, Click.COLLECTION_NAME, ClickPopulation::class.java
        )
    }

    override fun findClickCount(
        timeInterval: TimeInterval,
        id: String?,
        body: ClickCountRequest?
    ): AggregationResults<ClickTimeIntervalPopulation> {
        val pipeline = arrayListOf<AggregationOperation>()

        id?.let {
            pipeline.add(
                Aggregation.match(
                    Criteria.where(Click.URL_ID).`is`(it)
                )
            )
        }

        body?.let {
            pipeline.add(
                Aggregation.match(
                    Criteria().andOperator(Criteria.where(Click.CLICK_TIME).gte(body.start).lte(body.end))
                )
            )
        }

        when (timeInterval) {
            TimeInterval.DAY -> {
                pipeline.add(DAY_PROJECTION_STAGE)
                pipeline.add(DAY_GROUP_STAGE)
                pipeline.add(DAY_SORT_STAGE)
            }
            TimeInterval.HOUR -> {
                pipeline.add(HOUR_PROJECTION_STAGE)
                pipeline.add(HOUR_GROUP_STAGE)
                pipeline.add(HOUR_SORT_STAGE)
            }
        }

        val aggregation = Aggregation.newAggregation(
            pipeline
        )

        return mongoTemplate.aggregate(
            aggregation,
            Click.COLLECTION_NAME,
            ClickTimeIntervalPopulation::class.java
        )


    }


}