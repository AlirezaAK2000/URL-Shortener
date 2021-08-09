package com.example.clickservice.repository.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.util.*


@Document(ClickHistogram.COLLECTION_NAME)
class ClickHistogram(
    @Id
    val date: Date,

    @Field(COUNT)
    val count: Long

) {
    companion object {
        const val COLLECTION_NAME = "ClickHistogram"
        const val COUNT = "count"
        const val DATE = "date"
    }
}