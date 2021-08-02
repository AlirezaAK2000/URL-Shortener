package com.example.urlshortener.service

import com.example.urlshortener.controller.model.kafka.Click
import com.example.urlshortener.controller.model.response.ShortURLResponse
import com.example.urlshortener.controller.model.response.ShortURLUpdateResponse
import com.example.urlshortener.exceptions.URLIsNotValid
import com.example.urlshortener.repository.ShortURLRepository
import com.example.urlshortener.repository.entity.ShortURL
import com.example.urlshortener.service.model.shortURL.FindShortURLRequest
import com.example.urlshortener.service.model.shortURL.UpdateShortURLRequest
import com.example.urlshortener.utils.RandomString
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import javax.servlet.http.HttpServletResponse


@Service
class ShortURLService(
    val shortURLRepository: ShortURLRepository,
    @Value("\${custom.baseurl}")
    val BASE: String,
    val mongoTemplate: MongoTemplate,
    val kafkaTemplate: KafkaTemplate<String, String>,
    val mapper: ObjectMapper,
    @Value("\${custom.topicName}")
    val topicName: String,
    val URLMatcher: Pattern
) {

    val BASE_URL = "$BASE/api/url"

    companion object {

        @Value("\${custom.pageSize}")
        const val DEFAULT_PAGE_SIZE = 4
    }


    fun findAll(size: Int, pageNum: Int): Map<String, Any> {
        val pageable = PageRequest.of(pageNum ?: 0, size ?: DEFAULT_PAGE_SIZE)
        val dataPage: Page<ShortURLResponse> =
            shortURLRepository.findAll(Sort.by(Sort.Direction.DESC, ShortURL.CREATE_DATE), pageable).map {
                ShortURLResponse(it)
            }
        return createPagingResponse(dataPage)
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

    fun findById(id: String): ShortURLResponse? {
        val query = shortURLRepository.findById(id)
        return if (query.isPresent) ShortURLResponse(query.get()) else null
    }

    fun createShortURL(url: String): ShortURLResponse? {

        val matcher = URLMatcher.matcher(url)

        if (!matcher.find())
            throw URLIsNotValid()

        shortURLRepository.findByOriginalURLHash(url.hashCode())?.let {
            return ShortURLResponse(it)
        }

        var generatedKey = RandomString.getAlphaNumericString()
        while (shortURLRepository.findById(generatedKey).isPresent)
            generatedKey = RandomString.getAlphaNumericString()

        val obj = ShortURL(
            id = generatedKey,
            originalUrl = url
        )

        return ShortURLResponse(shortURLRepository.insert(obj))
    }

    fun deleteById(id: String): Unit = shortURLRepository.deleteById(id)

    fun deleteByOriginalURL(req: FindShortURLRequest): Unit =
        shortURLRepository.deleteByOriginalURLHash(req.originalURL.hashCode())

    fun findByOriginalURL(
        req: FindShortURLRequest
    ): ShortURLResponse = ShortURLResponse(shortURLRepository.findByOriginalURLHash(req.originalURL.hashCode())!!)

    fun updateOriginalURL(req: UpdateShortURLRequest): ShortURLUpdateResponse {

        val matcher = URLMatcher.matcher(req.newOriginalURL)

        if (!matcher.find())
            throw URLIsNotValid()

        val query = Query()
        query.addCriteria(
            Criteria.where(ShortURL.ORIGINAL_URL_HASH).`is`(req.originalURL.hashCode())
        )
        val update = Update()
        update.set(ShortURL.ORIGINAL_URL, req.newOriginalURL)
        update.set(ShortURL.ORIGINAL_URL_HASH, req.newOriginalURL.hashCode())
        return ShortURLUpdateResponse(
            updated = mongoTemplate.updateFirst(query, update, ShortURL::class.java).modifiedCount > 0
        )
    }

    private fun sendMessage(topic: String, msg: Click): Unit {
        kafkaTemplate.send(topic, mapper.writeValueAsString(msg))
    }

    fun redirectToOriginalURL(
        id: String,
        response: HttpServletResponse
    ): Unit {
        val query = shortURLRepository.findById(id)

        if (query.isPresent) {
            val obj = query.get()
            sendMessage(
                topicName, Click(
                    id = obj.id
                )
            )
            response.sendRedirect(obj.originalUrl)
        } else
            throw URLIsNotValid("there is no mapping for the specified URL")

    }

}