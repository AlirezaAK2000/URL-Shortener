package com.example.clickservice.controller.model

import com.example.clickservice.service.model.aggregation.population.ClickPopulation

data class ClickCountResponse(
    val URLId : String,
    val count : Long
){
    constructor(clickPopulation: ClickPopulation):this(
        URLId = clickPopulation.URLId,
        count = clickPopulation.count
    )
}
