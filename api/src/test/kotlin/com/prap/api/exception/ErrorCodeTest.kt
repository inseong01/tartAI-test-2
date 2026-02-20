package com.prap.api.exception

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class ErrorCodeTest {

    @Nested
    inner class `ErrorCode 테스트` {
        @Test
        fun `SERVICE_ERROR를 반환한다`() {
            val error = ErrorCode.SERVICE_ERROR

            assertEquals(error.code, "SERVICE_ERROR")
            assertEquals(error.message, "서비스 오류가 발생했습니다.")
            assertEquals(error.status, HttpStatus.INTERNAL_SERVER_ERROR)
        }

        @Test
        fun `UNKNOWN_ERROR를 반환한다`() {
            val error = ErrorCode.UNKNOWN_ERROR

            assertEquals(error.code, "UNKNOWN_ERROR")
            assertEquals(error.message, "알 수 없는 오류가 발생했습니다.")
            assertEquals(error.status, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @Nested
    inner class `CustomException 테스트` {
        @Test
        fun `사용자가 정의한 오류를 반환한다`() {
            ErrorCode.values().forEach {
                val exception = assertThrows<CustomException> {
                    throw CustomException(it)
                }

                assertEquals(it, exception.errorCode)
                assertEquals(null, exception.detail)
                assertEquals(it.message, exception.message)
            }
        }

        @Test
        fun `detail이 추가된 오류를 반환한다`() {
            val exception = assertThrows<CustomException> {
                throw CustomException(ErrorCode.UNKNOWN_ERROR, "세부내용")
            }

            assertEquals(ErrorCode.UNKNOWN_ERROR, exception.errorCode)
            assertEquals("세부내용", exception.detail)
            assertEquals(ErrorCode.UNKNOWN_ERROR.message, exception.message)
        }
    }
}