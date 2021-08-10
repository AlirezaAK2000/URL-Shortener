package com.example.urlshortener.service

import com.example.urlshortener.client.ClickHistogramClient
import com.example.urlshortener.client.model.ClickCountRequest
import com.example.urlshortener.client.model.ClickHistogramResponse
import com.example.urlshortener.enums.TimeInterval
import org.springframework.stereotype.Service


@Service
class ClickHistogramService(
    val clickHistogramClient: ClickHistogramClient
) {

    fun countClicks(
        timeInterval: TimeInterval,
        body: ClickCountRequest?
    ): List<ClickHistogramResponse> = if (body != null) clickHistogramClient.countClicks(
        timeInterval,
        body
    ) else clickHistogramClient.countClicksWithoutBody(
        timeInterval
    )

}
