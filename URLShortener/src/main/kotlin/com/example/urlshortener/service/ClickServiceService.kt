package com.example.urlshortener.service

import com.example.urlshortener.client.ClickServiceClient
import com.example.urlshortener.client.model.ClickCountRequest
import com.example.urlshortener.client.model.ClickCountResponse
import com.example.urlshortener.client.model.ClickResponse
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



}