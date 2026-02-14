package com.prap.api.exception

import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CustomExceptionHandlerTest {

    private val handler = CustomExceptionHandler()

    @Test
    fun `UNKNOWN_ERROR 오류를 반환한다`() {
        val customException = CustomException(ErrorCode.UNKNOWN_ERROR, "UNKNOWN_ERROR 오류 테스트")
        val output = handler.handleCustomException(customException)

        assertEquals(output.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)

        val body = output.body
        assertNotNull(body)
        assertEquals(body.code, ErrorCode.UNKNOWN_ERROR.code)
        assertEquals(body.message, ErrorCode.UNKNOWN_ERROR.message)
        assertEquals(body.status, ErrorCode.UNKNOWN_ERROR.status.value())
        assertEquals(body.detail, "UNKNOWN_ERROR 오류 테스트")
    }

    @Test
    fun `SERVICE_ERROR 오류를 반환한다`() {
        val customException = CustomException(ErrorCode.SERVICE_ERROR, "SERVICE_ERROR 오류 테스트")
        val output = handler.handleCustomException(customException)

        assertEquals(output.statusCode, HttpStatus.INTERNAL_SERVER_ERROR)

        val body = output.body
        assertNotNull(body)
        assertEquals(body.code, ErrorCode.SERVICE_ERROR.code)
        assertEquals(body.message, ErrorCode.SERVICE_ERROR.message)
        assertEquals(body.status, ErrorCode.SERVICE_ERROR.status.value())
        assertEquals(body.detail, "SERVICE_ERROR 오류 테스트")
    }
}