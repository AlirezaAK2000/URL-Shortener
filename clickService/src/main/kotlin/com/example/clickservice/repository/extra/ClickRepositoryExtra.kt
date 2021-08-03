package com.example.clickservice.repository.extra

import com.example.clickservice.service.model.aggregation.population.ClickPerDayPopulation
import com.example.clickservice.service.model.aggregation.population.ClickPerHourPopulation
import com.example.clickservice.service.model.aggregation.population.ClickPopulation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import java.util.*


interface ClickRepositoryExtra {
    fun findAllURLClickCountByDate(
        startDate: Date,
        endDate: Date
    ): AggregationResults<ClickPopulation>

    fun findAllURLClicksCountPerHour(): AggregationResults<ClickPerHourPopulation>

    fun findAllURLClicksCountPerDay(): AggregationResults<ClickPerDayPopulation>

    fun findAllURLClicksCountPerHourByURLId(URLId : String): AggregationResults<ClickPerHourPopulation>

    fun findAllURLClicksCountPerDayByURLId(URLId: String): AggregationResults<ClickPerDayPopulation>

}