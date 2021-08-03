package com.example.clickservice.service.model.aggregation.id

data class ClickPerDayPopulationId(
    val clickYear: Int,
    val clickDay: Int,
){
    companion object {
        const val YEAR = "clickYear"
        const val DAY = "clickDay"
    }
}
