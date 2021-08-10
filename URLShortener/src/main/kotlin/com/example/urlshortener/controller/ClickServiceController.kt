package com.example.urlshortener.controller

import com.example.urlshortener.client.model.ClickCountRequest
import com.example.urlshortener.client.model.ClickCountResponse
import com.example.urlshortener.client.model.ClickResponse
import com.example.urlshortener.enums.TimeInterval
import com.example.urlshortener.service.ClickServiceService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("api/clicks")
class ClickServiceController(
    val clickServiceService: ClickServiceService
) {

    @GetMapping("/count")
    fun countClicks(
        @RequestBody(required = false) body: ClickCountRequest?
    ): List<ClickCountResponse> = clickServiceService.countClicks(body)

    @GetMapping("/{id}")
    fun findByURLId(
        @PathVariable id: String
    ): List<ClickResponse>? = clickServiceService.findByURLId(id)


}