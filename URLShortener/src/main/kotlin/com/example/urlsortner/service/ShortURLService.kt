package com.example.urlsortner.service

import com.example.urlsortner.controller.model.kafka.Click
import com.example.urlsortner.exceptions.URLIsNotValid
import com.example.urlsortner.repository.ShortURLRepository
import com.example.urlsortner.repository.entity.ShortURL
import com.example.urlsortner.service.model.ShortURLRequest
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.lang.NullPointerException
import java.util.*
import java.util.regex.Pattern
import kotlin.streams.asSequence


@Service
class ShortURLService(
    val shortURLRepository: ShortURLRepository,
    @Value("\${custom.baseurl}")
    val BASE : String = ""
) {

    val BASE_URL = BASE + "/api/url"
    private val URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$"
    val URL_PATTERN = Pattern.compile(URL_REGEX);



    fun findAll(): MutableList<ShortURL> = shortURLRepository.findAll()

    fun findById(id: String): Optional<ShortURL> = shortURLRepository.findById(ObjectId(id))

    fun createShortURL(url: String): ShortURL? {

        val matcher = URL_PATTERN.matcher(url)

        if (!matcher.find())
            throw URLIsNotValid()

        val query : ShortURL? = shortURLRepository.findByOriginalUrl(url)

        if(query != null)
            return query

        val generatedKey = ObjectId.get()
        val shortedURL = arrayOf(BASE_URL, generatedKey.toString()).joinToString("/")

        val obj = ShortURL(
            id = generatedKey,
            shortedURL = shortedURL,
            originalUrl = url
        )

        return shortURLRepository.insert(obj)
    }

    fun deleteById(id: String):Unit = shortURLRepository.deleteById(ObjectId(id))

    fun update(req: ShortURLRequest): ShortURL? {
        val obj = req.id?.let { shortURLRepository.findById(ObjectId(it)) }
        val forUpdate: ShortURL = if (obj?.isPresent() == true) obj?.get() else throw NullPointerException()
        return shortURLRepository.save(
            forUpdate.apply {
                shortedURL = req.shortURL.toString()
                originalUrl = req.originalURL
            }
        )
    }

}