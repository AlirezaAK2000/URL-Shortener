package com.example.urlshortener.controller

import com.example.urlshortener.service.ShortURLService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


@RestController
@RequestMapping("")
class RedirectController(
    val shortURLService: ShortURLService
) {
    @GetMapping("/{id}")
    fun redirectToOriginalURL(
        @PathVariable id: String,
        response: HttpServletResponse
    ): Unit = shortURLService.redirectToOriginalURL(id, response)

}