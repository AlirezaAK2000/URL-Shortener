package com.example.clickservice.controller

import com.example.clickservice.controller.model.ClickCountResponse
import com.example.clickservice.controller.model.ClickResponse
import com.example.clickservice.controller.model.ClickTimeIntervalResponse
import com.example.clickservice.enums.TimeInterval
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
        @RequestBody(required = false) body: ClickCountRequest?
    ): List<ClickCountResponse> = clickService.findAllURLClickCountByDate(body)

    @GetMapping("/{id}")
    fun findByURLId(
        @PathVariable id: String
    ): List<ClickResponse>? = clickService.findByURLId(id)

    @PostMapping("/count/{timeInterval}")
    fun findClickCount(
        @PathVariable timeInterval : TimeInterval,
        @RequestParam(required = false) id : String?,
        @RequestBody(required = false) body: ClickCountRequest?
    ) : List<ClickTimeIntervalResponse> = clickService.findClickCount(timeInterval,id,body)

}