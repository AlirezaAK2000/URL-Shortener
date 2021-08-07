package com.example.urlshortener.client

import com.example.urlshortener.client.model.ClickCountRequest
import com.example.urlshortener.client.model.ClickCountResponse
import com.example.urlshortener.client.model.ClickResponse
import com.example.urlshortener.client.model.ClickTimeIntervalResponse
import com.example.urlshortener.enums.TimeInterval
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(name = "\${custom.microservice.name}", url = "\${custom.microservice.url}")
interface ClickServiceClient {
    @GetMapping("/count")
    fun countClicks(
        @RequestBody body: ClickCountRequest?
    ): List<ClickCountResponse>

    @GetMapping("/{id}")
    fun findByURLId(
        @PathVariable id: String
    ): List<ClickResponse>?

    @GetMapping("/count/{timeInterval}")
    fun findClickCountWithoutBody(
        @PathVariable timeInterval: TimeInterval,
        @RequestParam(required = false) id: String?,
    ): List<ClickTimeIntervalResponse>

    @GetMapping("/count/{timeInterval}")
    fun findClickCount(
        @PathVariable timeInterval: TimeInterval,
        @RequestParam(required = false) id: String?,
        @RequestBody(required = false) body: ClickCountRequest?
    ): List<ClickTimeIntervalResponse>
}