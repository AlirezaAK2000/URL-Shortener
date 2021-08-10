package com.example.urlshortener.client.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class ClickHistogramResponse(
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    val date: Date,
    val count: Long
)