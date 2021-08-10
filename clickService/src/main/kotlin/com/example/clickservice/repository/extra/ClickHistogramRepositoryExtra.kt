package com.example.clickservice.repository.extra

import com.example.clickservice.controller.model.ClickHistogramResponse
import com.example.clickservice.enums.TimeInterval
import com.example.clickservice.repository.entity.ClickHistogram
import com.example.clickservice.service.model.aggregation.population.ClickTimeIntervalPopulation
import com.example.clickservice.service.model.request.ClickCountRequest
import org.springframework.data.mongodb.core.aggregation.AggregationResults

interface ClickHistogramRepositoryExtra {

    fun bulkAddition(data: Map<String, Long>)

    fun countClicks(
        timeInterval: TimeInterval,
        body: ClickCountRequest?
    ): List<ClickHistogramResponse>



}