package com.example.clickservice.repository

import com.example.clickservice.repository.entity.Click
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ClickRepository : MongoRepository<Click , ObjectId>{
    fun findByURLId(URLId : String): List<Click>?
    fun countClicksByURLId(URL : String): Long
}