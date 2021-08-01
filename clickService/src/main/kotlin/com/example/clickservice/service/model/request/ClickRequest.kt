package com.example.clickservice.service.model.request

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class ClickRequest(
    val id : String?,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    val click_date : Date
    )