package com.example.clickservice.repository.extra

import com.example.clickservice.enums.TimeInterval
import com.example.clickservice.service.model.aggregation.population.ClickPopulation
import com.example.clickservice.service.model.aggregation.population.ClickTimeIntervalPopulation
import com.example.clickservice.service.model.request.ClickCountRequest
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import java.util.*


interface ClickRepositoryExtra {
    fun findAllURLClickCountByDate(
        body: ClickCountRequest?
    ): AggregationResults<ClickPopulation>

    fun findClickCount(timeInterval: TimeInterval, id: String?, body: ClickCountRequest?): AggregationResults<ClickTimeIntervalPopulation>

}