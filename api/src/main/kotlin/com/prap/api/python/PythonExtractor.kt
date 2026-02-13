package com.prap.api.python

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.io.File
import java.nio.charset.StandardCharsets
import com.fasterxml.jackson.module.kotlin.readValue
import com.prap.api.model.ArticleDto

@Component
class PythonExecutor(
    private val pythonRunner: PythonRunner,
    private val objectMapper: ObjectMapper
) {

    fun runScraper(): List<ArticleDto> {
        val output = this.pythonRunner.run()

        try {
            return objectMapper.readValue(output)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}
