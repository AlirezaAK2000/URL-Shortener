package com.example.urlshortener.repository.impl

import com.example.urlshortener.repository.entity.ShortURL
import com.example.urlshortener.repository.extra.ShortURLRepositoryExtra
import com.example.urlshortener.service.model.shortURL.UpdateShortURLRequest
import com.example.urlshortener.utils.HashLib
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository


@Repository
class ShortURLRepositoryImpl(
    private val mongoTemplate: MongoTemplate
) : ShortURLRepositoryExtra {
    override fun updateByOriginalURL(req: UpdateShortURLRequest): Boolean {
        val query = Query()
        query.addCriteria(
            Criteria.where(ShortURL.ORIGINAL_URL_HASH).`is`(HashLib.generateHash(req.originalURL))
        )
        val update = Update()
        update.set(ShortURL.ORIGINAL_URL, req.newOriginalURL)
        update.set(ShortURL.ORIGINAL_URL_HASH, HashLib.generateHash(req.newOriginalURL))
        return mongoTemplate.updateFirst(query, update, ShortURL::class.java).modifiedCount > 0
    }
}