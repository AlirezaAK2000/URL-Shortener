package com.example.urlshortener.repository.impl

import com.example.urlshortener.repository.entity.ShortURL
import com.example.urlshortener.repository.extra.ShortUrlRepoExtra
import com.example.urlshortener.service.model.shortURL.UpdateShortURLRequest
import com.example.urlshortener.utils.HashLib
import com.mongodb.client.result.UpdateResult
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository


@Repository
class ShortUrlRepoExtraImpl(
    private val mongoTemplate: MongoTemplate
) : ShortUrlRepoExtra {
    override fun updateOriginalURL(req: UpdateShortURLRequest): UpdateResult {
        val query = Query()
        query.addCriteria(
            Criteria.where(ShortURL.ORIGINAL_URL_HASH).`is`(HashLib.generateHash(req.originalURL))
        )
        val update = Update()
        update.set(ShortURL.ORIGINAL_URL, req.newOriginalURL)
        update.set(ShortURL.ORIGINAL_URL_HASH, HashLib.generateHash(req.newOriginalURL))
        return mongoTemplate.updateFirst(query, update, ShortURL::class.java)
    }
}