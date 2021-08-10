package com.example.urlshortener.controller

import com.example.urlshortener.client.model.ClickCountRequest
import com.example.urlshortener.client.model.ClickHistogramResponse
import com.example.urlshortener.enums.TimeInterval
import com.example.urlshortener.service.ClickHistogramService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/hist")
class ClickHistogramController(
    val clickHistogramService: ClickHistogramService
) {

    @PostMapping("")
    fun countClicks(
        @RequestParam timeInterval : TimeInterval,
        @RequestBody(required = false) body: ClickCountRequest?
    ) : List<ClickHistogramResponse> = clickHistogramService.countClicks(
        timeInterval,
        body
    )
}