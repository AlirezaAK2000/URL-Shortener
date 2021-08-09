package com.example.clickservice.repository

import com.example.clickservice.repository.entity.Click
import com.example.clickservice.repository.extra.ClickRepositoryExtra
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ClickRepository : MongoRepository<Click, String>, ClickRepositoryExtra {
    fun findByURLId(URLId: String): List<Click>?
}