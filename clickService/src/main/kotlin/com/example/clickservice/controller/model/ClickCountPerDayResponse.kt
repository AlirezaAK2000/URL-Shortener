package com.example.clickservice.controller.model

import com.example.clickservice.service.model.aggregation.id.ClickPerDayPopulationId
import com.example.clickservice.service.model.aggregation.population.ClickPerDayPopulation

data class ClickCountPerDayResponse(
    val _id : ClickPerDayPopulationId,
    val count: Long
){
    constructor(clickPerDayPopulation: ClickPerDayPopulation):this(
        _id = clickPerDayPopulation._id ,
        count = clickPerDayPopulation.count
    )
}