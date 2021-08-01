package com.example.urlshortener.controller.model.response

import com.example.urlshortener.repository.entity.ShortURL
import java.util.*

data class ShortURLResponse(
    val id: String,
    val originalURL: String,
    val createDate : Date
){

    constructor(obj : ShortURL ) : this (
        id = obj.id,
        originalURL = obj.originalUrl,
        createDate = obj.createDate
    )

}