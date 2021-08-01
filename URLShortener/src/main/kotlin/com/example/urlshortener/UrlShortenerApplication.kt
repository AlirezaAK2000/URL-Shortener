package com.example.urlshortener

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean


@SpringBootApplication
class UrlShortenerApplication{
    @Bean
    fun createObjectMapper(): ObjectMapper = jacksonObjectMapper()

}

fun main(args: Array<String>) {
    runApplication<UrlShortenerApplication>(*args)
}
