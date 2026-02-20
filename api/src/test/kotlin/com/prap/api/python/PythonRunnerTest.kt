package com.prap.api.python

import org.junit.jupiter.api.Nested
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PythonRunnerTest {

    private val SUCCESS_FILE_COMMAND: String = "scraper.success_main"
    private val EMPTY_FILE_COMMAND: String = "scraper.empty_main"
    private val FAILED_FILE_COMMAND: String = "scraper.failed_main"

    @Nested
    inner class `getDirPathFile 테스트` {

        @Test
        fun `존재하는 디렉터리면 정상 반환`() {
            val runner = PythonRunner("src/test/resources")

            val dirFile = runner.getDirPathFile()

            assertTrue(dirFile.exists())
            assertTrue(dirFile.isDirectory)
        }

        @Test
        fun `존재하지 않는 디렉터리면 예외 발생`() {
            val runner = PythonRunner("not_exist_path")

            assertFailsWith<IllegalArgumentException> {
                runner.getDirPathFile()
            }
        }
    }

    @Nested
    inner class `createScraperProcess 테스트` {
        @Test
        fun `테스트용 파이썬 성공 파일 실행`() {
            val runner = PythonRunner("src/test/resources")
            val pathFile = runner.getDirPathFile()

            val output = runner.createScraperProcess(pathFile, SUCCESS_FILE_COMMAND)

            assertNotNull(output)
        }
    }

    @Nested
    inner class `readProcessOutput 테스트` {

        @Test
        fun `process output을 읽는다`() {
            val runner = PythonRunner("src/test/resources")
            val expected = "äöü 한글 ✓ 🚀"
            val mockProcess = mock<Process>()

            whenever(mockProcess.inputStream).thenReturn(
                ByteArrayInputStream(expected.toByteArray(StandardCharsets.UTF_8))
            )

            val result = runner.readProcessOutput(mockProcess)

            assertEquals(expected, result)
        }
    }

    @Nested
    inner class `run 테스트` {

        @Test
        fun `run 성공 - ok 출력`() {
            val runner = PythonRunner("src/test/resources")

            val result = runner.run(SUCCESS_FILE_COMMAND).trim()

            assertEquals("äöü 한글 ✓ \uD83D\uDE80", result)
        }

        @Test
        fun `run 성공 - 빈 문자열 출력`() {
            val runner = PythonRunner("src/test/resources")

            assertFailsWith<RuntimeException> {
                runner.run(EMPTY_FILE_COMMAND)
            }
        }

        @Test
        fun `exitCode 0 아니면 예외`() {
            val runner = PythonRunner("src/test/resources")

            assertFailsWith<RuntimeException> {
                runner.run(FAILED_FILE_COMMAND)
            }
        }
    }
}