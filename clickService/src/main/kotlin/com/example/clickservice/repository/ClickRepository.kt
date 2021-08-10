package com.example.clickservice.repository

import com.example.clickservice.repository.entity.Click
import com.example.clickservice.repository.extra.ClickRepositoryExtra
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ClickRepository : MongoRepository<Click, String>, ClickRepositoryExtra {
    fun findByURLId(URLId: String): List<Click>?
}