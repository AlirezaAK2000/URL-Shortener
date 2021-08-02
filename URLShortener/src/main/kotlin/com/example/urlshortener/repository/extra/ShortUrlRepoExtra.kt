package com.example.urlshortener.repository.extra

import com.example.urlshortener.controller.model.response.ShortURLUpdateResponse
import com.example.urlshortener.service.model.shortURL.UpdateShortURLRequest
import com.mongodb.client.result.UpdateResult

interface ShortUrlRepoExtra {
    fun updateOriginalURL(req: UpdateShortURLRequest): UpdateResult

}