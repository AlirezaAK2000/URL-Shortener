package com.example.urlsortner.controller.model.kafka

import java.util.*

data class Click(
    val id : String,
    val clickTime : Date = Date()
)