package com.example.urlsortner.controller

import com.example.urlsortner.controller.model.kafka.Click
import com.example.urlsortner.controller.model.response.ShortURLResponse
import com.example.urlsortner.service.ShortURLService
import com.example.urlsortner.service.model.ShortURLRequest
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("/api/url")
class ShortURLController(
    val shortURLService: ShortURLService,
    val kafkaTemplate: KafkaTemplate<String, String>,
    val mapper: ObjectMapper,
    @Value("\${custom.topicName}")
    val topicName : String
) {

    @GetMapping
    fun findAllShortURLs(
        @RequestBody body: ShortURLRequest
    ): List<ShortURLResponse> {
        val query = shortURLService.findAll()
        return query.map {
            ShortURLResponse.fromEntity(it)
        }
    }


    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: String,
        response: HttpServletResponse
    ): String? {

        val query = shortURLService.findById(id)

        if (query.isPresent()) {
            val obj = query.get()
            sendMessage(
                topicName, Click(
                    id = obj.id.toString()
                )
            )
            response.sendRedirect(obj.originalUrl)
        }
        return null

    }

    private fun sendMessage(topic: String, msg: Click) {
        kafkaTemplate.send(topic, mapper.writeValueAsString(msg))
    }

    @PostMapping
    fun createShortURL(
        @RequestBody body: ShortURLRequest
    ): ShortURLResponse {
        val response = shortURLService.createShortURL(body.originalURL)
        return ShortURLResponse.fromEntity(response!!)
    }


}