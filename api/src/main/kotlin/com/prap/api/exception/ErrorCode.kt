package com.prap.api.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val code: String, val message: String, val status: HttpStatus) {
    SERVICE_ERROR("SERVICE_ERROR", "서비스 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNKNOWN_ERROR("UNKNOWN_ERROR", "알 수 없는 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR)
}

class CustomException(val errorCode: ErrorCode, val detail: String? = null) : RuntimeException(errorCode.message)