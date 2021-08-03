package com.example.clickservice.controller.model

import com.example.clickservice.repository.entity.Click

data class ClickCountResponse(
    val URLId : String,
    val count : Long
)