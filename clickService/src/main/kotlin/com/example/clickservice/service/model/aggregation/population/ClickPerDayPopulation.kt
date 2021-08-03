package com.example.clickservice.service.model.aggregation.population

import com.example.clickservice.service.model.aggregation.id.ClickPerDayPopulationId
import org.springframework.data.annotation.Id

data class ClickPerDayPopulation(
    @Id
    val _id : ClickPerDayPopulationId,
    val count: Long
){
    companion object {
        const val COUNT = "count"
    }
}