package com.example.urlshortener.client.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class ClickCountRequest(
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    val start : Date?,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    val end : Date?
)