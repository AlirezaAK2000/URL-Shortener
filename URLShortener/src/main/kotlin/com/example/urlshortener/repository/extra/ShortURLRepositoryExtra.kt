package com.example.urlshortener.repository.extra

import com.example.urlshortener.service.model.shortURL.UpdateShortURLRequest

interface ShortURLRepositoryExtra {
    fun updateByOriginalURL(req: UpdateShortURLRequest): Boolean
}