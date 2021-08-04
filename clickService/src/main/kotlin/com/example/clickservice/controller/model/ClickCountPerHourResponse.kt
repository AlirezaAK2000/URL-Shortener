package com.example.clickservice.controller.model

import com.example.clickservice.service.model.aggregation.id.ClickTimeIntervalPopulationId


class ClickCountPerHourResponse(
    val _id : ClickTimeIntervalPopulationId,
    val count: Long
){
    constructor(clickPerHourPopulation: ClickPerHourPopulation) : this(
        _id  = clickPerHourPopulation._id,
        count = clickPerHourPopulation.count
    )
}