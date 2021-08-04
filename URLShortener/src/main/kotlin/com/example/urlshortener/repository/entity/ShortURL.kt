package com.example.urlshortener.repository.entity

import com.example.urlshortener.utils.HashLib
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*


@Document(ShortURL.COLLECTION_NAME)
class ShortURL(

    @Id
    val id: String,

    @Field(ORIGINAL_URL)
    val originalURL :String,

    @Field(CREATE_DATE)
    val createDate : Date = Date(),

    @Indexed
    @Field(ORIGINAL_URL_HASH)
    val originalURLHash:String = HashLib.generateHash(originalURL)

){

    companion object {
        const val ORIGINAL_URL = "originalURL"
        const val COLLECTION_NAME = "shortURLs"
        const val ID = "id"
        const val CREATE_DATE = "createDate"
        const val ORIGINAL_URL_HASH = "originalURLHash"
    }

}
