package com.example.clickservice.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import java.util.concurrent.ConcurrentHashMap


@EnableKafka
@Configuration
internal class ConsumerConfig {

    @Bean
    fun createObjectMapper(): ObjectMapper = jacksonObjectMapper()

    @Bean
    fun createConcurrentMap(): ConcurrentHashMap<String, Long> = ConcurrentHashMap<String, Long>()
}