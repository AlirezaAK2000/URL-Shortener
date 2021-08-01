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
    ): List<ClickCountResponse> {
        println(body)
        val res = clickService.findAllURLClickCountByDate(
            startDate = body.start,
            endDate = body.end
        )
        return res.map {
            ClickCountResponse(
                count = it.count,
                URLId = it.URLId
            )
        }

    }
    @GetMapping
    fun findAllClicks(): List<ClickResponse> {
        val clicks = clickService.findAll()

        return clicks.map {
            ClickResponse.fromEntity(it)
        }
    }
}