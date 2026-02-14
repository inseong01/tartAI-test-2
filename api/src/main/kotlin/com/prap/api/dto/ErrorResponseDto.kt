package com.prap.api.dto

import jakarta.validation.constraints.NotBlank

data class ErrorResponseDto(
    @field:NotBlank(message = "코드는 필수입니다.")
    val code: String = "",

    @field:NotBlank(message = "메시지는 필수입니다.")
    val message: String = "",

    val status: Int? = null,

    val detail: String? = null,
)
