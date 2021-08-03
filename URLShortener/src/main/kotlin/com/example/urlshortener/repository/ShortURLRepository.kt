package com.example.urlshortener.repository

import com.example.urlshortener.repository.entity.ShortURL
import com.example.urlshortener.repository.extra.ShortURLRepositoryExtra
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ShortURLRepository : MongoRepository<ShortURL , String>, ShortURLRepositoryExtra {
    fun findByOriginalURLHash(hash: String): ShortURL?
    fun deleteByOriginalURLHash(hash: String)
    override fun findAll(pageable: Pageable): Page<ShortURL>
}