package com.example.urlshortener.repository

import com.example.urlshortener.repository.entity.ShortURL
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ShortURLRepository : MongoRepository<ShortURL , String>{
    fun findByOriginalURLHash(hash : Int) : ShortURL?
    fun deleteByOriginalURLHash(hash : Int) : Unit

}