package com.example.clickservice.controller.model

import com.example.clickservice.service.model.aggregation.id.ClickPerHourPopulationId
import com.example.clickservice.service.model.aggregation.population.ClickPerHourPopulation


class ClickCountPerHourResponse(
    val _id : ClickPerHourPopulationId,
    val count: Long
){
    constructor(clickPerHourPopulation: ClickPerHourPopulation) : this(
        _id  = clickPerHourPopulation._id,
        count = clickPerHourPopulation.count
    )
}