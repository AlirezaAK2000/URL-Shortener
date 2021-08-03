package com.example.urlshortener.service

import com.example.urlshortener.controller.model.kafka.Click
import com.example.urlshortener.controller.model.response.ShortURLResponse
import com.example.urlshortener.controller.model.response.ShortURLUpdateResponse
import com.example.urlshortener.exceptions.URLIsNotValid
import com.example.urlshortener.repository.ShortURLRepository
import com.example.urlshortener.repository.entity.ShortURL
import com.example.urlshortener.service.model.shortURL.FindShortURLRequest
import com.example.urlshortener.service.model.shortURL.UpdateShortURLRequest
import com.example.urlshortener.utils.HashLib
import com.example.urlshortener.utils.RandomString
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.regex.Pattern


@Service
class ShortURLService(
    val shortURLRepository: ShortURLRepository,
    @Value("\${custom.baseurl}")
    private val BASE: String,
    val kafkaTemplate: KafkaTemplate<String, String>,
    val mapper: ObjectMapper,
    @Value("\${custom.topicName}")
    val topicName: String,
    val URLMatcher: Pattern
) {

    private val BASE_URL = "$BASE/api/url"

    companion object {
        @Value("\${custom.pageSize}")
        const val DEFAULT_PAGE_SIZE = 4
    }

    fun findAll(size: Integer?, pageNum: Integer?): Map<String, Any> {
        val pageable = PageRequest.of((pageNum ?: 0) as Int,
            (size ?: DEFAULT_PAGE_SIZE) as Int, Sort.Direction.DESC ,ShortURL.CREATE_DATE )
        val dataPage: Page<ShortURLResponse> =
            shortURLRepository.findAll(pageable).map {
                ShortURLResponse(it)
            }
        return createPagingResponse(dataPage)
    }

    fun findById(id: String): ShortURLResponse? {
        val query = shortURLRepository.findById(id)
        return if (query.isPresent) ShortURLResponse(query.get()) else null
    }

    fun createShortURL(url: String): ShortURLResponse? {
        validateURL(url)

        shortURLRepository.findByOriginalURLHash(HashLib.generateHash(url))?.let {
            return ShortURLResponse(it)
        }

        var generatedKey = RandomString.getAlphaNumericString()
        while (shortURLRepository.findById(generatedKey).isPresent)
            generatedKey = RandomString.getAlphaNumericString()

        val obj = ShortURL(
            id = generatedKey,
            originalURL = url
        )

        return ShortURLResponse(shortURLRepository.insert(obj))
    }

    fun deleteById(id: String): Unit = shortURLRepository.deleteById(id)

    fun deleteByOriginalURL(req: FindShortURLRequest): Unit =
        shortURLRepository.deleteByOriginalURLHash(HashLib.generateHash(req.originalURL))

    fun findByOriginalURL(
        req: FindShortURLRequest
    ): ShortURLResponse =
        ShortURLResponse(shortURLRepository.findByOriginalURLHash(HashLib.generateHash(req.originalURL))!!)


    fun updateOriginalURL(req: UpdateShortURLRequest): ShortURLUpdateResponse {
        validateURL(req.newOriginalURL)
        val res = shortURLRepository.updateByOriginalURL(req)
        return ShortURLUpdateResponse(
            updated = res
        )
    }

    fun redirectToOriginalURL(
        id: String
    ): String? {
        val query = shortURLRepository.findById(id)

        if (query.isPresent) {
            val obj = query.get()
            sendMessage(
                topicName, Click(id = obj.id)
            )
            return obj.originalURL
        } else
            throw URLIsNotValid("there is no mapping for the specified URL")

    }

    private fun createPagingResponse(
        dataPage: Page<ShortURLResponse>
    ): Map<String, Any> {
        val data = dataPage.content
        val numberOfPages = dataPage.totalPages
        val numberOfItems = dataPage.totalElements
        val currentPage = dataPage.number
        return mapOf(
            "data" to data,
            "numberOfPages" to numberOfPages,
            "numberOfItems" to numberOfItems,
            "currentPage" to currentPage
        )
    }

    private fun sendMessage(topic: String, msg: Click) {
        kafkaTemplate.send(topic, mapper.writeValueAsString(msg))
    }

    private fun validateURL(url: String) {
        val matcher = URLMatcher.matcher(url)

        if (!matcher.find())
            throw URLIsNotValid()
    }

}