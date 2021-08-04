package com.example.clickservice.service.model.aggregation.id

data class ClickTimeIntervalPopulationId(
    val clickYear: Int,
    val ClickDate: Int,
    val ClickHour :Int?
){
    companion object {
        const val YEAR = "clickYear"
        const val DAY = "ClickDate"
        const val HOUR = "ClickHour"
    }
}
