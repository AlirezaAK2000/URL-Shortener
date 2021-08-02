package com.example.urlshortener.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.regex.Pattern


@Configuration
class Tools {
    companion object {
        const val URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$"
    }

    @Bean
    fun createObjectMapper(): ObjectMapper = jacksonObjectMapper()

    @Bean
    fun createURLMatcher(): Pattern = Pattern.compile(URL_REGEX)

}