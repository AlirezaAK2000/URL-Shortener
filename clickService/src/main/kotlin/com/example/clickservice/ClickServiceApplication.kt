package com.example.clickservice

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaAdmin

@SpringBootApplication
class ClickServiceApplication

fun main(args: Array<String>) {
    runApplication<ClickServiceApplication>(*args)
}
