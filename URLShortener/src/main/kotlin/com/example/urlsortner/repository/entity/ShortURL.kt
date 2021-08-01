package com.example.urlsortner.repository.entity

import com.example.urlsortner.service.model.ShortURLRequest
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations


@Document("ShortURL")
class ShortURL(

    @Indexed
    @Id
    val id: ObjectId = ObjectId.get(),

    @Indexed
    @Field(ORIGINAL_URL)
    var originalUrl :String,

    @Indexed
    @Field(SHORTURL)
    var shortedURL: String
){

    companion object {
        const val ORIGINAL_URL = "original_url"
        const val SHORTURL = "short_url"


    }

}
