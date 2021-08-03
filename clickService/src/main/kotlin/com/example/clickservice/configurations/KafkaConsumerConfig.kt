package com.example.clickservice.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka


@EnableKafka
@Configuration
internal class KafkaConsumerConfig {

    @Bean
    fun createObjectMapper(): ObjectMapper = jacksonObjectMapper()
}