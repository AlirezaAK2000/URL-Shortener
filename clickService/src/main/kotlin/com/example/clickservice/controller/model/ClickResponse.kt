package com.example.clickservice.controller.model

import com.example.clickservice.repository.entity.Click
import com.fasterxml.jackson.annotation.JsonFormat
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

data class ClickResponse(
    val id: String,
    val URLId: String,
    val clickTime: Date?
){
    constructor(click: Click):this(
        id = click.id.toString(),
        URLId = click.URLId,
        clickTime = click.clickTime
    )

}

