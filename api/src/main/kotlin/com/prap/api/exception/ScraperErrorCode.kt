package com.prap.api.exception

import org.springframework.http.HttpStatus

enum class ScraperErrorCode(
    val code: String,
    val httpStatus: HttpStatus,
    val message: String
) {
    BAD_REQUEST("BAD_REQUEST", HttpStatus.BAD_REQUEST, "잘못된 엔드포인트 접근입니다."),
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드 요청입니다."),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다.")
}