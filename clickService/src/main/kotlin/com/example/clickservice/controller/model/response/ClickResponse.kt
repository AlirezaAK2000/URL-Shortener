package com.example.clickservice.controller.model.response

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "UTC")
    val clickTime: Date?
){
    companion object{
        fun fromEntity(click: Click): ClickResponse{
            return ClickResponse(
                id = click.id.toString(),
                URLId = click.URLId,
                clickTime = click.clickTime
            )
        }
    }
}

