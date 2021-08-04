package com.example.urlshortener.client

import com.example.urlshortener.client.model.ClickCountPerDayResponse
import com.example.urlshortener.client.model.ClickCountPerHourResponse
import com.example.urlshortener.client.model.ClickCountResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@FeignClient(name="\${custom.microservice.name}", url="\${custom.microservice.url}")
interface ClickServiceClient {
    @GetMapping("/count/{id}")
    fun findClickCountByURLId(
        @PathVariable id: String
    ): ClickCountResponse

    @GetMapping("/count/hour")
    fun findAllURLClicksCountPerHour(): List<ClickCountPerHourResponse>

    @GetMapping("/count/day")
    fun findAllURLClicksCountPerDay(): List<ClickCountPerDayResponse>

    @GetMapping("/count/hour/{id}")
    fun findAllURLClicksCountPerHourByURLId(@PathVariable id: String): List<ClickCountPerHourResponse>

    @GetMapping("/count/day/{id}")
    fun findAllURLClicksCountPerDayByURLId(@PathVariable id: String): List<ClickCountPerDayResponse>
}