package com.example.clickservice.service

import com.example.clickservice.controller.model.ClickCountResponse
import com.example.clickservice.controller.model.ClickResponse
import com.example.clickservice.controller.model.ClickTimeIntervalResponse
import com.example.clickservice.enums.TimeInterval
import com.example.clickservice.repository.ClickRepository
import com.example.clickservice.service.model.request.ClickCountRequest
import org.springframework.stereotype.Service


@Service
class ClickService(
    val clickRepository: ClickRepository
) {

    fun findByURLId(URLId: String) = clickRepository.findByURLId(URLId)?.map { ClickResponse(it) }

    fun findAllURLClickCountByDate(body: ClickCountRequest?): List<ClickCountResponse> =
        clickRepository.findAllURLClickCountByDate(body).mappedResults.map {
            ClickCountResponse(it)
        }

    fun findClickCount(
        timeInterval: TimeInterval,
        id: String?,
        body: ClickCountRequest?
    ): List<ClickTimeIntervalResponse> =
        clickRepository.findClickCount(timeInterval, id, body).mappedResults.map {
            ClickTimeIntervalResponse(it)
        }
}


