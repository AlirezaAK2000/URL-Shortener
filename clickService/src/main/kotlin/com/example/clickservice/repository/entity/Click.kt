package com.example.clickservice.repository.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@Document(Click.COLLECTION_NAME)
data class Click (
    @Id
    val id : ObjectId = ObjectId.get(),

    @Field(URL_ID)
    val URLId :String,

//    @Indexed
    @Field(CLICK_TIME)
    val clickTime : Date?
){
    companion object{
        const val CLICK_TIME = "click_date"
        const val URL_ID = "url_id"
        const val COLLECTION_NAME = "click"
    }
}