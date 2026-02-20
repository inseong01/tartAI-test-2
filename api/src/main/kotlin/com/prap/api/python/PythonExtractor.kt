package com.prap.api.python

import com.prap.api.dto.ArticleDto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component

@Component
class PythonExecutor(
    private val pythonRunner: PythonRunner,
    private val objectMapper: ObjectMapper
) {

    fun runScraper(): List<ArticleDto> {
        try {
            val output = this.pythonRunner.run("scraper.main")
            return objectMapper.readValue(output)
        } catch (e: Exception) {
            throw RuntimeException(e.message)
        }
    }

}
