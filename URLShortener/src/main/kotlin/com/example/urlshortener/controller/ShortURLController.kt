package com.example.urlshortener.controller

import com.example.urlshortener.controller.model.response.ShortURLResponse
import com.example.urlshortener.controller.model.response.ShortURLUpdateResponse
import com.example.urlshortener.service.ShortURLService
import com.example.urlshortener.service.model.shortURL.CreateShortURLRequest
import com.example.urlshortener.service.model.shortURL.FindShortURLRequest
import com.example.urlshortener.service.model.shortURL.UpdateShortURLRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/urls")
class ShortURLController(
    val shortURLService: ShortURLService,
) {
    @GetMapping("")
    fun findAllShortURLs(
        @RequestParam(required = false) page: Int,
        @RequestParam(required = false) size: Int
    ): Map<String, Any> = shortURLService.findAll(size = size, pageNum = page)

    @PostMapping("")
    fun createShortURL(
        @RequestBody body: CreateShortURLRequest
    ): ShortURLResponse? = shortURLService.createShortURL(body.originalURL)

    @PutMapping("")
    fun updateOriginalURL(
        @RequestBody body: UpdateShortURLRequest
    ): ShortURLUpdateResponse? = shortURLService.updateOriginalURL(body)

    @GetMapping("/{id}")
    fun findById(
        @PathVariable id: String
    ): ShortURLResponse? = shortURLService.findById(id)

    @DeleteMapping("/{id}")
    fun deleteById(
        @PathVariable id: String
    ): Unit = shortURLService.deleteById(id)

    @GetMapping("/orgurl")
    fun findByOriginalURL(
        @RequestBody body: FindShortURLRequest
    ): ShortURLResponse = shortURLService.findByOriginalURL(body)

    @DeleteMapping("")
    fun deleteByOriginalURL(
        @RequestBody body: FindShortURLRequest
    ): Unit = shortURLService.deleteByOriginalURL(body)
}