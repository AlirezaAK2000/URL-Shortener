package com.example.urlsortner.repository

import com.example.urlsortner.repository.entity.ShortURL
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ShortURLRepository : MongoRepository<ShortURL , ObjectId>{
    fun findByOriginalUrl(originalUrl : String) : ShortURL?
}