package com.prap.api.exception

import org.springframework.http.HttpStatus

class ScraperException(
    val code: String,
    val message: String,
    val detail: Map<String, Any>? = null,
    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
)