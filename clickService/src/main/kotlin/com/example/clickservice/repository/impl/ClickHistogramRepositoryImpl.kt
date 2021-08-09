package com.example.clickservice.repository.impl

import com.example.clickservice.repository.entity.ClickHistogram
import com.example.clickservice.repository.extra.ClickHistogramRepositoryExtra
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.MongoTemplate
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

    companion object{
        val DATE_FORMAT: DateFormat = SimpleDateFormat("yyyy/MM/dd HH")
    }

    override fun bulkAddition(data: Map<String, Long>) {
        val bulk: BulkOperations = mongoTemplate.bulkOps(BulkOperations.BulkMode.ORDERED , ClickHistogram::class.java)
        for (key in data.keys){
            val update : Update = Update().inc(ClickHistogram.COUNT , data[key]!!)
            val query : Query = Query(Criteria.where(ClickHistogram.DATE).`is`(
                DATE_FORMAT.parse(key)
            ))
            bulk.upsert(query , update)
        }
        bulk.execute()
    }
}