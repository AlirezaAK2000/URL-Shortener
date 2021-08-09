package com.example.urlshortener.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.BAD_REQUEST)
class URLIsNotValid(msg: String = "url is not valid!") : RuntimeException(msg)