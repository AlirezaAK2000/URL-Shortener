package com.example.urlshortener.service

import com.example.urlshortener.client.ClickServiceClient
import com.example.urlshortener.client.model.ClickCountRequest
import com.example.urlshortener.client.model.ClickCountResponse
import com.example.urlshortener.client.model.ClickResponse
import com.example.urlshortener.client.model.ClickTimeIntervalResponse
import com.example.urlshortener.enums.TimeInterval
import org.springframework.stereotype.Service

@Service
class ClickServiceService(
    val clickServiceClient: ClickServiceClient
) {
    fun countClicks(
        body: ClickCountRequest?
    ): List<ClickCountResponse> = clickServiceClient.countClicks(body)

    fun findByURLId(
        id: String
    ): List<ClickResponse>? = clickServiceClient.findByURLId(id)

    fun findClickCount(
        timeInterval: TimeInterval,
        id: String?,
        body: ClickCountRequest?
    ): List<ClickTimeIntervalResponse> = if (body == null) clickServiceClient.findClickCountWithoutBody(
        timeInterval,
        id
    ) else clickServiceClient.findClickCount(timeInterval, id ,body)

}