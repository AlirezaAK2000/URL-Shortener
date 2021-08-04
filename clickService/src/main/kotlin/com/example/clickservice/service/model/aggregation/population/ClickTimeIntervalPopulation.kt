package com.example.clickservice.service.model.aggregation.population

import com.example.clickservice.service.model.aggregation.id.ClickTimeIntervalPopulationId
import org.springframework.data.annotation.Id

data class ClickTimeIntervalPopulation(
    @Id
    val _id : ClickTimeIntervalPopulationId,
    val count: Long
){
    companion object {
        const val COUNT = "count"
    }
}