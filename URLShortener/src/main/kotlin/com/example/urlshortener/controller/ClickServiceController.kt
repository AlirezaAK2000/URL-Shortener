package com.example.urlshortener.controller

import com.example.urlshortener.client.model.ClickCountPerDayResponse
import com.example.urlshortener.client.model.ClickCountPerHourResponse
import com.example.urlshortener.client.model.ClickCountResponse
import com.example.urlshortener.service.ClickServiceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/clicks")
class ClickServiceController(
    val clickServiceService: ClickServiceService
) {

    @GetMapping("/count/{id}")
    fun findClickCountByURLId(
        @PathVariable id: String
    ): ClickCountResponse = clickServiceService.findClickCountByURLId(id)

    @GetMapping("/count/hour")
    fun findAllURLClicksCountPerHour(): List<ClickCountPerHourResponse> =
        clickServiceService.findAllURLClicksCountPerHour()

    @GetMapping("/count/day")
    fun findAllURLClicksCountPerDay(): List<ClickCountPerDayResponse> =
        clickServiceService.findAllURLClicksCountPerDay()

    @GetMapping("/count/hour/{id}")
    fun findAllURLClicksCountPerHourByURLId(@PathVariable id: String): List<ClickCountPerHourResponse> =
        clickServiceService.findAllURLClicksCountPerHourByURLId(id)

    @GetMapping("/count/day/{id}")
    fun findAllURLClicksCountPerDayByURLId(@PathVariable id: String): List<ClickCountPerDayResponse> =
        clickServiceService.findAllURLClicksCountPerDayByURLId(id)
}