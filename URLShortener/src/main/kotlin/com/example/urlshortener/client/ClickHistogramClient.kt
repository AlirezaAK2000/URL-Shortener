package com.example.urlshortener.client

import com.example.urlshortener.client.model.ClickCountRequest
import com.example.urlshortener.client.model.ClickHistogramResponse
import com.example.urlshortener.enums.TimeInterval
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.*


@FeignClient(name = "\${custom.microservice.controllers.histogram.name}", url = "\${custom.microservice.controllers.histogram.url}")
interface ClickHistogramClient {
    @PostMapping("")
    fun countClicks(
        @RequestParam timeInterval: TimeInterval,
        @RequestBody(required = false) body: ClickCountRequest?
    ): List<ClickHistogramResponse>


    @PostMapping("")
    fun countClicksWithoutBody(
        @RequestParam timeInterval: TimeInterval,
    ): List<ClickHistogramResponse>


}