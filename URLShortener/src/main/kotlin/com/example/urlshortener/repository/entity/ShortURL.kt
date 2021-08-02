package com.example.urlshortener.repository.entity

import com.example.urlshortener.utils.HashLib
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*


@Document(ShortURL.DB_NAME)
class ShortURL(

    @Indexed
    @Id
    val id: String,

    @Field(ORIGINAL_URL)
    val originalUrl :String,

    @Field(CREATE_DATE)
    val createDate : Date = Date(),

    @Field(ORIGINAL_URL_HASH)
    val originalURLHash:String = HashLib.generateHash(originalUrl)

){

    companion object {
        const val ORIGINAL_URL = "original_url"
        const val DB_NAME = "shortURLs"
        const val ID = "id"
        const val CREATE_DATE = "create_date"
        const val ORIGINAL_URL_HASH = "original_url_hash"
    }

}
