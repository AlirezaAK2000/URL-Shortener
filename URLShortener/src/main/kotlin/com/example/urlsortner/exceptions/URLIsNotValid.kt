package com.example.urlsortner.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus


@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
class URLIsNotValid(msg: String = "url is not valid!") : RuntimeException(msg)