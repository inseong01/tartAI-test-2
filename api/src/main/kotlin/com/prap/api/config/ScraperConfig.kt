package com.prap.api.config

import com.prap.api.python.PythonExecutor
import com.prap.api.python.PythonRunner

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ScraperConfig {

    @Bean
    fun pythonRunner(
        @Value("\${scraper.path}") scraperPath: String
    ): PythonRunner {
        return PythonRunner(scraperPath)
    }

    @Bean
    fun pythonExecutor(pythonRunner: PythonRunner, objectMapper: ObjectMapper): PythonExecutor {
        return PythonExecutor(pythonRunner, objectMapper)
    }
}
