package com.prap.api.controller

import com.prap.api.dto.ArticleDto
import com.prap.api.handler.ScraperExceptionHandler
import com.prap.api.python.PythonExecutor
import com.prap.api.python.PythonRunner
import com.prap.api.service.ScrapingService

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
            val handler = ScraperExceptionHandler()
            val output = handler.unknownError(mapOf("message" to e.message))
            val json = this.mapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(output)

            return ResponseEntity.ok(json)
        }

        val json = this.mapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(articles)
        return ResponseEntity.ok(json)
    }
}