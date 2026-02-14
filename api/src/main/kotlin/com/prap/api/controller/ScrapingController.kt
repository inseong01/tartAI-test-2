package com.prap.api.controller

import com.prap.api.dto.ArticleDto
import com.prap.api.python.PythonExecutor
import com.prap.api.python.PythonRunner
import com.prap.api.service.ScrapingService
import com.prap.api.exception.CustomException
import com.prap.api.exception.ErrorCode

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
class ScrapingController {

    private final val mapper = ObjectMapper()

    @GetMapping("/encoding-test")
    fun encodingTest(): String {
        return "한글 테스트 성공"
    }

    @GetMapping("/python-test")
    fun pythonTest(): ResponseEntity<String> {
        val pythonRunner = PythonRunner()
        val pythonExecutor = PythonExecutor(pythonRunner, this.mapper)
        val scrapingService = ScrapingService(pythonExecutor)

        var articles: List<ArticleDto> = emptyList()

        try {
            articles = scrapingService.getArticles()
        } catch (e: ResponseStatusException) {
            throw CustomException(ErrorCode.SERVICE_ERROR, e.reason)
        }

        val json = this.mapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(articles)
        return ResponseEntity.ok(json)
    }
}