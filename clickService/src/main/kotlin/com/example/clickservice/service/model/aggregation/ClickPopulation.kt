package com.example.clickservice.service.model.aggregation

import com.example.clickservice.repository.entity.Click
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

data class ClickPopulation(
    @Id
    val URLId :String,
    val count : Long
){
    companion object{
        const val COUNT = "count"
    }
}