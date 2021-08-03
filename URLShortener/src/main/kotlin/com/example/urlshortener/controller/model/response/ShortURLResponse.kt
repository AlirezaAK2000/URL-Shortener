package com.example.urlshortener.controller.model.response

import com.example.urlshortener.repository.entity.ShortURL
import com.fasterxml.jackson.annotation.JsonFormat
import java.util.*

data class ShortURLResponse(
    val id: String,
    val originalURL: String,
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    val createDate : Date
){

    constructor(obj : ShortURL ) : this (
        id = obj.id,
        originalURL = obj.originalURL,
        createDate = obj.createDate
    )

}