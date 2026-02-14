package com.prap.api.exception

import com.prap.api.dto.ErrorResponseDto

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(ex: CustomException): ResponseEntity<ErrorResponseDto> {
        val error = ErrorResponseDto(
            code = ex.errorCode.code,
            message = ex.errorCode.message,
            status = ex.errorCode.status.value(),
            detail = ex.detail
        )
        return ResponseEntity.status(ex.errorCode.status).body(error)
    }

}