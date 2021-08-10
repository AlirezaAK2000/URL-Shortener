package com.example.urlshortener.client

import com.example.urlshortener.client.model.ClickCountRequest
import com.example.urlshortener.client.model.ClickCountResponse
import com.example.urlshortener.client.model.ClickResponse
import com.example.urlshortener.enums.TimeInterval
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "\${custom.microservice.controllers.clicks.name}", url = "\${custom.microservice.controllers.clicks.url}")
interface ClickServiceClient {
    @GetMapping("/count")
    fun countClicks(
        @RequestBody body: ClickCountRequest?
    ): List<ClickCountResponse>

    @GetMapping("/{id}")
    fun findByURLId(
        @PathVariable id: String
    ): List<ClickResponse>?

}