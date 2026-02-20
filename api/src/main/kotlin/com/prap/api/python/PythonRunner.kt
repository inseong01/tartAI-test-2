package com.prap.api.python

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File

@Component
class PythonRunner(
    @Value("\${scraper.path:../scraper}")
    private val scraperPath: String
) {

    fun getDirPathFile(): File {
        val pythonDir = File(this.scraperPath)

        require(pythonDir.exists()) { "Python directory not found: $pythonDir" }

        return pythonDir
    }

    fun createScraperProcess(pythonDirFile: File, packageName: String): Process {
        return ProcessBuilder(
            "python3",
            "-X", "utf8",
            "-m", packageName
        )
            .directory(pythonDirFile)
            .redirectErrorStream(true)
            .apply {
                environment()["PYTHONIOENCODING"] = "UTF-8"
            }
            .start()
    }

    fun readProcessOutput(process: Process): String {
        return process.inputStream.reader(Charsets.UTF_8).readText()
    }

    fun run(packageName: String): String {
        val pythonDir = this.getDirPathFile()
        val process = this.createScraperProcess(pythonDir, packageName)
        val output = this.readProcessOutput(process)

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