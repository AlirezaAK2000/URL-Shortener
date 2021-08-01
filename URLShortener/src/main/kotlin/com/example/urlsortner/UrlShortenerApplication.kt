package com.example.urlsortner

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource("classpath:application.yml")
class UrlShortenerApplication{

    @Bean
    fun createObjectMapper(): ObjectMapper = jacksonObjectMapper()

}

fun main(args: Array<String>) {
    runApplication<UrlShortenerApplication>(*args)
}
