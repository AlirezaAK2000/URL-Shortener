package com.example.urlshortener.client.model

data class ClickCountPerDayResponse(
    val _id : ClickPerDayPopulationId,
    val count: Long
)