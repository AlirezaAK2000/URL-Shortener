package com.example.urlshortener.enums

import com.fasterxml.jackson.annotation.JsonProperty

enum class TimeInterval {
    @JsonProperty("hour")
    HOUR,
    @JsonProperty("day")
    DAY
}