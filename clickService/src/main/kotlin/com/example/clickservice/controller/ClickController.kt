package com.example.clickservice.controller

import com.example.clickservice.controller.model.ClickCountPerDayResponse
import com.example.clickservice.controller.model.ClickCountPerHourResponse
import com.example.clickservice.controller.model.ClickCountResponse
import com.example.clickservice.controller.model.ClickResponse
import com.example.clickservice.service.model.request.ClickCountRequest
import com.example.clickservice.service.ClickService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/clicks")
class ClickController(
    val clickService: ClickService
) {

    @GetMapping("/count")
    fun countClicks(
        @RequestBody body: ClickCountRequest
    ): List<ClickCountResponse> = clickService.countClicks(body)

    @GetMapping("/{id}")
    fun findByURLId(
        @PathVariable id: String
    ): List<ClickResponse>? = clickService.findByURLId(id)

    @GetMapping("/count/{id}")
    fun findClickCountByURLId(
        @PathVariable id: String
    ): ClickCountResponse = clickService.countClicksByURLId(id)

    @GetMapping("/count/hour")
    fun findAllURLClicksCountPerHour(): List<ClickCountPerHourResponse> =
        clickService.findAllURLClicksCountPerHour()

    @GetMapping("/count/day")
    fun findAllURLClicksCountPerDay(): List<ClickCountPerDayResponse> =
        clickService.findAllURLClicksCountPerDay()

    @GetMapping("/count/hour/{id}")
    fun findAllURLClicksCountPerHourByURLId(@PathVariable id: String): List<ClickCountPerHourResponse> =
        clickService.findAllURLClicksCountPerHourByURLId(id)

    @GetMapping("/count/day/{id}")
    fun findAllURLClicksCountPerDayByURLId(@PathVariable id: String): List<ClickCountPerDayResponse> =
        clickService.findAllURLClicksCountPerDayByURLId(id)
}