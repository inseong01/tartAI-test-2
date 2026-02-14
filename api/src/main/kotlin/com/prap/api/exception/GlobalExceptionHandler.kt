package com.prap.api.exception

import com.prap.api.dto.ErrorResponseDto

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleUnsupportedMethod(ex: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorResponseDto> {
        val error = ErrorResponseDto(
            code = HttpStatus.METHOD_NOT_ALLOWED.name,
            message = "지원하지 않는 HTTP 메서드 요청입니다.",
            status = HttpStatus.METHOD_NOT_ALLOWED.value(),
            detail = "지원되지 않는 메서드: ${ex.method}"
        )
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoFoundException(ex: NoHandlerFoundException): ResponseEntity<ErrorResponseDto> {
        val error = ErrorResponseDto(
            code = HttpStatus.BAD_REQUEST.name,
            message = "잘못된 엔드포인트 접근입니다.",
            status = HttpStatus.BAD_REQUEST.value(),
            detail = ex.message
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error)
    }
    
    @ExceptionHandler(Exception::class)
    fun handleUnknownError(ex: Exception): ResponseEntity<ErrorResponseDto> {
        val error = ErrorResponseDto(
            code = HttpStatus.INTERNAL_SERVER_ERROR.name,
            message = "예기치 않은 오류가 발생했습니다.",
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            detail = ex.message
        )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error)
    }

}