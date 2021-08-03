package com.example.clickservice.consumers

import com.example.clickservice.consumers.model.ClickMessage
import com.example.clickservice.repository.ClickRepository
import com.example.clickservice.repository.entity.Click
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class ClickConsumers(
    val clickRepository: ClickRepository,
    val objectMapper: ObjectMapper
) {
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
    }

}