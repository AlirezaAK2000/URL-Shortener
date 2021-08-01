package com.example.clickservice.service

import com.example.clickservice.controller.model.kafka.ClickMessage
import com.example.clickservice.controller.model.response.ClickCountResponse
import com.example.clickservice.controller.model.response.ClickResponse
import com.example.clickservice.repository.ClickRepository
import com.example.clickservice.repository.entity.Click
import com.example.clickservice.service.model.aggregation.ClickPopulation
import com.example.clickservice.service.model.request.ClickCountRequest
import com.example.clickservice.service.model.request.ClickRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*


@Service
class ClickService(
    val clickRepository: ClickRepository,
    val mongoTemplate: MongoTemplate,
    val objectMapper: ObjectMapper,
) {

    private final val dateFormatter = SimpleDateFormat(DATE_FORMAT)
    init {
        dateFormatter.timeZone = TimeZone.getTimeZone("UTC")

    }
    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS\'Z\'"
    }

    @KafkaListener(
        topics = ["\${custom.topic}"],
        groupId = "store-click"
    )
    fun clickListener(msg: String) {
        println(msg)
        val obj = objectMapper.readValue(msg, ClickMessage::class.java)
        clickRepository.insert(
            Click(
                URLId = obj.id,
                clickTime = obj.clickTime
            )
        )
    }

    fun findByURLId(URLId: String) = clickRepository.findByURLId(URLId)?.map { ClickResponse(it) }

    fun countClicksByURLId(body : ClickCountRequest) : List<ClickCountResponse> {
        val res = findAllURLClickCountByDate(
            startDate = body.start,
            endDate = body.end
        )
        return res.map {
            ClickCountResponse(
                count = it.count,
                URLId = it.URLId
            )
        }
    }

    private fun findAllURLClickCountByDate(
        startDate: Date,
        endDate : Date
    ) : MutableList<ClickPopulation>{

        val criteria = Criteria().andOperator(Criteria.where(Click.CLICK_TIME).gte(startDate).lte(endDate))
        val matcherStage = Aggregation.match(criteria)
        val groupStage = Aggregation.group(Click.URL_ID).count().`as`(ClickPopulation.COUNT)
        val sortStage = Aggregation.sort(Sort.by(Sort.Direction.DESC, ClickPopulation.COUNT))
        val aggregation = Aggregation.newAggregation(
            matcherStage,
            groupStage,
            sortStage
        )
        val result = mongoTemplate.aggregate(
            aggregation , Click.COLLECTION_NAME , ClickPopulation::class.java
        )

        return result.mappedResults
    }

    fun findAll() = clickRepository.findAll().map { ClickResponse(it) }


}


