package com.prap.api.service

import com.prap.api.dto.ArticleDto
import com.prap.api.python.PythonExecutor

import jakarta.validation.Validation
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ScrapingService(
    private val pythonExecutor: PythonExecutor
) {

    fun getArticles(): List<ArticleDto> {
        var articles: List<ArticleDto> = emptyList()

        try {
            articles = this.pythonExecutor.runScraper()
        } catch (e: RuntimeException) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.localizedMessage)
        }

        val validator = Validation.buildDefaultValidatorFactory().validator

        val hasViolations = articles.any { validator.validate(it).isNotEmpty() }
        if (hasViolations) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return articles
    }
}