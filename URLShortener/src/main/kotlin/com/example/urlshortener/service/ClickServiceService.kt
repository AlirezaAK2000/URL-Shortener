package com.example.urlshortener.service

import com.example.urlshortener.client.ClickServiceClient
import com.example.urlshortener.client.model.ClickCountPerDayResponse
import com.example.urlshortener.client.model.ClickCountPerHourResponse
import com.example.urlshortener.client.model.ClickCountResponse
import org.springframework.stereotype.Service

@Service
class ClickServiceService(
    val clickServiceClient: ClickServiceClient
) {
    fun findClickCountByURLId(
        id: String
    ): ClickCountResponse = clickServiceClient.findClickCountByURLId(id)

    fun findAllURLClicksCountPerHour(): List<ClickCountPerHourResponse> =
        clickServiceClient.findAllURLClicksCountPerHour()

    fun findAllURLClicksCountPerDay(): List<ClickCountPerDayResponse> = clickServiceClient.findAllURLClicksCountPerDay()

    fun findAllURLClicksCountPerHourByURLId(id: String): List<ClickCountPerHourResponse> =
        clickServiceClient.findAllURLClicksCountPerHourByURLId(id)

    fun findAllURLClicksCountPerDayByURLId(id: String): List<ClickCountPerDayResponse> =
        clickServiceClient.findAllURLClicksCountPerDayByURLId(id)

}