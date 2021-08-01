package com.example.clickservice.controller.model.kafka

import java.util.*

data class ClickMessage(
    val id : String,
    val clickTime : Date?,
)