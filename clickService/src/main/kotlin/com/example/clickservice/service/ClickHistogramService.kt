package com.example.clickservice.service

import com.example.clickservice.controller.model.ClickHistogramResponse
import com.example.clickservice.enums.TimeInterval
import com.example.clickservice.repository.ClickHistogramRepository
import com.example.clickservice.repository.entity.ClickHistogram
import com.example.clickservice.service.model.request.ClickCountRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class ClickHistogramService(
    val clickHistogramRepository: ClickHistogramRepository
) {
    fun findAll(): List<ClickHistogram> =
        clickHistogramRepository.findAll(Sort.by(Sort.Direction.DESC, ClickHistogram.DATE))


    fun countClicks(
        timeInterval: TimeInterval,
        body: ClickCountRequest?
    ): List<ClickHistogramResponse> = clickHistogramRepository.countClicks(
        timeInterval,
        body
    )


}