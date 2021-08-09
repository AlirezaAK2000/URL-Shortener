package com.example.clickservice.repository.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


@Document(Click.COLLECTION_NAME)
class Click (
    @Id
    val id : String = ObjectId.get().toString(),

    @Field(URL_ID)
    val URLId :String,

    @Field(CLICK_TIME)
    val clickTime : Date?
){
    companion object{
        const val CLICK_TIME = "clickDate"
        const val URL_ID = "URLId"
        const val COLLECTION_NAME = "Clicks"
    }
}
