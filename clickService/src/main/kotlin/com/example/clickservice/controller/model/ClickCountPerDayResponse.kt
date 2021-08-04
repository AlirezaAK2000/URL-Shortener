package com.example.clickservice.controller.model

data class ClickCountPerDayResponse(
    val _id : ClickPerDayPopulationId,
    val count: Long
){
    constructor(clickPerDayPopulation: ClickPerDayPopulation):this(
        _id = clickPerDayPopulation._id ,
        count = clickPerDayPopulation.count
    )
}