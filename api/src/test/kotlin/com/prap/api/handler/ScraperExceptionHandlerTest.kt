package com.prap.api.handler

import org.springframework.http.HttpStatus
import kotlin.test.Test
import kotlin.test.assertEquals

class ScraperExceptionHandlerTest {

    private val scraperHandler = ScraperExceptionHandler()

    @Test
    fun `잘못된 경로로 접근하는 오류 객체를 반환한다`() {
        val exception = this.scraperHandler.invalidEndpoint()

        assertEquals(null, exception.detail)
        assertEquals(HttpStatus.BAD_REQUEST, exception.status)
        assertEquals("잘못된 엔드포인트 접근입니다.", exception.message)
        assertEquals("BAD_REQUEST", exception.code)
    }

    @Test
    fun `허용되지 않는 매서드 요청 오류 객체를 반환한다`() {
        val exception = this.scraperHandler.unsupportedMethod()

        assertEquals(null, exception.detail)
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, exception.status)
        assertEquals("지원하지 않는 HTTP 메서드 요청입니다.", exception.message)
        assertEquals("METHOD_NOT_ALLOWED", exception.code)
    }

    @Test
    fun `알 수 없는 오류 객체를 반환한다`() {
        val exception = this.scraperHandler.unknownError()

        assertEquals(null, exception.detail)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.status)
        assertEquals("알 수 없는 오류가 발생했습니다.", exception.message)
        assertEquals("INTERNAL_SERVER_ERROR", exception.code)
    }
}
