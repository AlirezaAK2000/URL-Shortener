package com.example.clickservice.repository

import com.example.clickservice.repository.entity.Click
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface ClickRepository : MongoRepository<Click , ObjectId>{
    fun findByURLId(URLId : String): Click?
    fun countClicksByURLId(URL : String): Int
}