package com.example.clickservice.enums

import com.fasterxml.jackson.annotation.JsonProperty

enum class TimeInterval {
    @JsonProperty("hour")
    HOUR,
    @JsonProperty("day")
    DAY
}