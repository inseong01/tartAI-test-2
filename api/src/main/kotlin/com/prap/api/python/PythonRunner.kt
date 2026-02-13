package com.prap.api.python

import org.springframework.stereotype.Component
import java.io.File
import java.nio.charset.StandardCharsets

@Component
class PythonRunner {

    private fun getScraperProcess(): Process {
            val pythonDir = File("../scraper")
            require(pythonDir.exists()) { "Python directory not found: $pythonDir" }

            return ProcessBuilder(
                "python",
                "-X", "utf8",
                "-m", "scraper.main"
            )
                .directory(pythonDir)
                .redirectErrorStream(true)
                .apply {
                    environment()["PYTHONIOENCODING"] = "UTF-8"
                }
                .start()
    }

    fun run(): String {
            val process = this.getScraperProcess()

            val output = process.inputStream
                .bufferedReader(StandardCharsets.UTF_8)
                .readText()

            val exitCode = process.waitFor()

            if (exitCode != 0) {
                throw RuntimeException("Python process failed. exitCode=$exitCode\n$output")
            }

            if (output.isBlank()) {
                throw RuntimeException("Python returned empty output")
            }

            return output
        }
    }