package com.example.urlshortener.client.model

import java.util.*

data class ClickResponse(
    val id: String,
    val URLId: String,
    val clickTime: Date?
)