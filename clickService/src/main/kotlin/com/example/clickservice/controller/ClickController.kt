package com.example.clickservice.controller

import com.example.clickservice.controller.model.response.ClickCountResponse
import com.example.clickservice.controller.model.response.ClickResponse
import com.example.clickservice.service.model.request.ClickCountRequest
import com.example.clickservice.service.ClickService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/click")
class ClickController(
    val clickService: ClickService
) {

    @GetMapping("/count")
    fun countClicksByURLId(
        @RequestBody body: ClickCountRequest
    ): List<ClickCountResponse> = clickService.countClicksByURLId(body)

    @GetMapping
    fun findAllClicks(): List<ClickResponse> = clickService.findAll()

    @GetMapping("/{id}")
    fun findByURLId(
        @PathVariable id : String
    ) : List<ClickResponse>? = clickService.findByURLId(id)
}