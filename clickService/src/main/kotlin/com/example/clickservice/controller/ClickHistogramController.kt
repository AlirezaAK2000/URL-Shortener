package com.example.clickservice.controller

import com.example.clickservice.controller.model.ClickHistogramResponse
import com.example.clickservice.enums.TimeInterval
import com.example.clickservice.service.ClickHistogramService
import com.example.clickservice.service.model.request.ClickCountRequest
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