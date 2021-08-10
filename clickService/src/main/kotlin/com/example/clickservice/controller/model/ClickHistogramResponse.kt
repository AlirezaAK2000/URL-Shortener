package com.example.clickservice.controller.model

import com.example.clickservice.repository.entity.ClickHistogram
import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class ClickHistogramResponse(
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    val date: Date?,
    val count: Long
){
    constructor(clickHistogram: ClickHistogram):this(
        date = clickHistogram.date,
        count = clickHistogram.count
    )
}