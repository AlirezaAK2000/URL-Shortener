package com.example.clickservice.service.model.aggregation.population

import com.example.clickservice.service.model.aggregation.id.ClickPerHourPopulationId
import org.springframework.data.annotation.Id

data class ClickPerHourPopulation(
    @Id
    val _id : ClickPerHourPopulationId,
    val count: Long
){
    companion object {
        const val COUNT = "count"
    }
}