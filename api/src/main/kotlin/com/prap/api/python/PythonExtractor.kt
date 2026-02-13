package com.prap.api.python

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import com.fasterxml.jackson.module.kotlin.readValue
import com.prap.api.dto.ArticleDto

@Component
class PythonExecutor(
    private val pythonRunner: PythonRunner,
    private val objectMapper: ObjectMapper
) {

    fun runScraper(): List<ArticleDto> {
        try {
            val output = this.pythonRunner.run()
            return objectMapper.readValue(output)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}
