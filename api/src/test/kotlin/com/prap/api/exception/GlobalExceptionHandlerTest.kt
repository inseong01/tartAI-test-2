package com.prap.api.exception

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.servlet.NoHandlerFoundException

class GlobalExceptionHandlerTest {

    private val handler = GlobalExceptionHandler()

    @Test
    fun `지원하지 않는 HTTP 메서드 처리 테스트`() {
        val ex = HttpRequestMethodNotSupportedException("POST")
        val response = handler.handleUnsupportedMethod(ex)

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.statusCode)

        val body = response.body
        assertNotNull(body)
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.name, body.code)
        assertEquals("지원하지 않는 HTTP 메서드 요청입니다.", body.message)
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED.value(), body.status)
        assertEquals("지원되지 않는 메서드: POST", body.detail)
    }

    @Test
    fun `잘못된 엔드포인트 접근 처리 테스트`() {
        val emptyHeaders = HttpHeaders()
        val ex = NoHandlerFoundException("GET", "/invalid", emptyHeaders)
        val response = handler.handleNoFoundException(ex)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)

        val body = response.body
        assertNotNull(body)
        assertEquals(HttpStatus.BAD_REQUEST.name, body.code)
        assertEquals("잘못된 엔드포인트 접근입니다.", body.message)
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.status)
        assertEquals(ex.message, body.detail)
    }

    @Test
    fun `예기치 않은 오류 처리 테스트`() {
        val ex = RuntimeException("서버 오류 발생")
        val response = handler.handleUnknownError(ex)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)

        val body = response.body
        assertNotNull(body)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name, body.code)
        assertEquals("예기치 않은 오류가 발생했습니다.", body.message)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.status)
        assertEquals(ex.message, body.detail)
    }
}
