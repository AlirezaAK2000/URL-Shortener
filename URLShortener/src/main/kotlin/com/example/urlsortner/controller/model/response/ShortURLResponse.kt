package com.example.urlsortner.controller.model.response

import com.example.urlsortner.repository.entity.ShortURL

data class ShortURLResponse(
    val id: String?,
    val shortURL: String?,
    val originalURL: String
){
    companion object {
        fun fromEntity(obj: ShortURL) : ShortURLResponse {
            return ShortURLResponse(
                id = obj.id.toString(),
                shortURL = obj.shortedURL,
                originalURL = obj.originalUrl
            )
        }
    }

}