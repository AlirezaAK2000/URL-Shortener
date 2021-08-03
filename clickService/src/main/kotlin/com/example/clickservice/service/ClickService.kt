package com.example.clickservice.service

import com.example.clickservice.controller.model.ClickCountPerDayResponse
import com.example.clickservice.controller.model.ClickCountPerHourResponse
import com.example.clickservice.controller.model.ClickCountResponse
import com.example.clickservice.controller.model.ClickResponse
import com.example.clickservice.repository.ClickRepository
import com.example.clickservice.service.model.request.ClickCountRequest
import org.springframework.stereotype.Service


@Service
class ClickService(
    val clickRepository: ClickRepository
) {

    fun countClicksByURLId(
        id: String
    ): ClickCountResponse = ClickCountResponse(count = clickRepository.countClicksByURLId(id), URLId = id)

    fun findByURLId(URLId: String) = clickRepository.findByURLId(URLId)?.map { ClickResponse(it) }

    fun countClicks(body: ClickCountRequest): List<ClickCountResponse> = clickRepository.findAllURLClickCountByDate(
        startDate = body.start,
        endDate = body.end
    ).mappedResults.map {
        ClickCountResponse(
            count = it.count,
            URLId = it.URLId
        )
    }

    fun findAllURLClicksCountPerHour(): List<ClickCountPerHourResponse> =
        clickRepository.findAllURLClicksCountPerHour().mappedResults.map {
            ClickCountPerHourResponse(it)
        }

    fun findAllURLClicksCountPerDay(): List<ClickCountPerDayResponse> =
        clickRepository.findAllURLClicksCountPerDay().mappedResults.map {
            ClickCountPerDayResponse(it)
        }


    fun findAllURLClicksCountPerHourByURLId(URLId: String): List<ClickCountPerHourResponse> =
        clickRepository.findAllURLClicksCountPerHourByURLId(URLId).mappedResults.map {
            ClickCountPerHourResponse(it)
        }

    fun findAllURLClicksCountPerDayByURLId(URLId: String): List<ClickCountPerDayResponse> =
        clickRepository.findAllURLClicksCountPerDayByURLId(URLId).mappedResults.map {
            ClickCountPerDayResponse(it)
        }

    fun findAll() = clickRepository.findAll().map { ClickResponse(it) }

}


