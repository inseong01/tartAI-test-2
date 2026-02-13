package com.prap.api.handler

import com.prap.api.exception.ScraperErrorCode
import com.prap.api.exception.ScraperException
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class ScraperExceptionHandler {

    private fun buildException(
        errorCode: ScraperErrorCode,
        detail: Map<String, Any>? = null
    ): ScraperException {
        return ScraperException(
            code = errorCode.code,
            message = errorCode.message,
            status = errorCode.httpStatus,
            detail = detail
        )
    }

    fun invalidEndpoint(detail: Map<String, Any>? = null) = buildException(ScraperErrorCode.BAD_REQUEST, detail)

    fun unsupportedMethod(detail: Map<String, Any>? = null) =
        buildException(ScraperErrorCode.METHOD_NOT_ALLOWED, detail)

    fun unknownError(detail: Map<String, Any>? = null) = buildException(ScraperErrorCode.INTERNAL_SERVER_ERROR, detail)
}
