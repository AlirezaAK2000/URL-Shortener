package com.example.urlshortener.service.model.shortURL

data class UpdateShortURLRequest(
    val originalURL : String,
    val newOriginalURL : String
)