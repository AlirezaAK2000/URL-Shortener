package com.example.clickservice.consumers

import com.example.clickservice.consumers.model.ClickMessage
import com.example.clickservice.repository.ClickRepository
import com.example.clickservice.repository.entity.Click
import com.fasterxml.jackson.databind.ObjectMapper
import org.bson.types.ObjectId
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap


@Component
class ClickConsumers(
    val clickRepository: ClickRepository,
    val objectMapper: ObjectMapper,
    val concurrentHashMap: ConcurrentHashMap<String, Long>
) {

    companion object {
        val DATE_FORMAT: DateFormat = SimpleDateFormat("yyyy/MM/dd HH")
    }

    @KafkaListener(
        topics = ["\${custom.topic}"],
        groupId = "store-click"
    )
    fun clickListener(msg: String) {
        val obj = objectMapper.readValue(msg, ClickMessage::class.java)
        clickRepository.insert(
            Click(
                URLId = obj.id,
                clickTime = obj.clickTime
            )
        )
        val date = DATE_FORMAT.format(Date())

        if (concurrentHashMap.contains(date))
            concurrentHashMap[date]?.let { concurrentHashMap.put(date, it + 1) }
        else
            concurrentHashMap[date] = 1


    }

}