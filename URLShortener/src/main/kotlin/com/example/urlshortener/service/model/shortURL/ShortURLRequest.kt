package com.example.urlshortener.service.model.shortURL

import java.util.*

data class ShortURLRequest(
    val originalURL : String,
    val createDate : Date?,
    val id : String
)
