package com.example.clickservice.controller.model

import com.example.clickservice.service.model.aggregation.id.ClickTimeIntervalPopulationId
import com.example.clickservice.service.model.aggregation.population.ClickTimeIntervalPopulation

data class ClickTimeIntervalResponse(
    val _id : ClickTimeIntervalPopulationId,
    val count: Long
){
    constructor(clickPerDayPopulation: ClickTimeIntervalPopulation):this(
        _id = clickPerDayPopulation._id ,
        count = clickPerDayPopulation.count
    )
}