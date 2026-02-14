package com.prap.api.config

import com.prap.api.python.PythonExecutor
import com.prap.api.python.PythonRunner

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ScraperConfig {

    @Bean
    fun pythonRunner(): PythonRunner = PythonRunner()

    @Bean
    fun pythonExecutor(pythonRunner: PythonRunner, objectMapper: ObjectMapper): PythonExecutor {
        return PythonExecutor(pythonRunner, objectMapper)
    }
}
