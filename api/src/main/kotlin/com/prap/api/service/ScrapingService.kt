package com.prap.api.service

import com.prap.api.dto.ArticleDto
import com.prap.api.exception.CustomException
import com.prap.api.exception.ErrorCode
import com.prap.api.python.PythonExecutor

import jakarta.validation.Validation
import org.springframework.stereotype.Service

@Service
class ScrapingService(
    private val pythonExecutor: PythonExecutor
) {

    fun getArticles(): List<ArticleDto> {
        val articles =
            try {
                this.pythonExecutor.runScraper()
            } catch (e: RuntimeException) {
                throw CustomException(ErrorCode.SERVICE_ERROR, "스크래퍼 실행 오류가 발생했습니다.")
            }

        val validator = Validation.buildDefaultValidatorFactory().validator

        val hasViolations = articles.any { validator.validate(it).isNotEmpty() }
        if (hasViolations) {
            throw CustomException(ErrorCode.SERVICE_ERROR, "스크래핑 데이터 검증 오류가 발생했습니다.")
        }

        return articles
    }
}