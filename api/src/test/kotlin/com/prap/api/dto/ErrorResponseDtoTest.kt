package com.prap.api.dto

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertTrue

class ErrorResponseDtoValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator
    private val mapper = ObjectMapper()

    @Test
    fun `올바른 구조면 ErrorResponseDto 검증을 통과한다`() {
        val CORRECT_JSON = """
            [
                {
                    "code": "ERROR_001",
                    "message": "에러가 발생했습니다.",
                    "status": 500,
                    "detail": "추가 정보"
                }
            ]
        """.trimIndent()

        val dtos: List<ErrorResponseDto> = mapper.readValue(CORRECT_JSON)

        dtos.forEach {
            val violations = validator.validate(it)
            assertTrue(violations.isEmpty())
        }
    }

    @Test
    fun `code가 비어있으면 ErrorResponseDto 검증을 통과하지 못한다`() {
        val EMPTY_CODE_JSON = """
            [
                {
                    "code": "",
                    "message": "에러 메시지",
                    "status": 500,
                    "detail": "추가 정보"
                }
            ]
        """.trimIndent()

        val dtos: List<ErrorResponseDto> = mapper.readValue(EMPTY_CODE_JSON)

        dtos.forEach {
            val violations = validator.validate(it)
            assertTrue(violations.isNotEmpty())
        }
    }

    @Test
    fun `message가 비어있으면 ErrorResponseDto 검증을 통과하지 못한다`() {
        val EMPTY_MESSAGE_JSON = """
            [
                {
                    "code": "ERROR_002",
                    "message": "",
                    "status": 500
                }
            ]
        """.trimIndent()

        val dtos: List<ErrorResponseDto> = mapper.readValue(EMPTY_MESSAGE_JSON)

        dtos.forEach {
            val violations = validator.validate(it)
            assertTrue(violations.isNotEmpty())
        }
    }

    @Test
    fun `status와 detail은 null이어도 검증 통과`() {
        val NULL_OPTIONAL_JSON = """
            [
                {
                    "code": "ERROR_003",
                    "message": "테스트"
                }
            ]
        """.trimIndent()

        val dtos: List<ErrorResponseDto> = mapper.readValue(NULL_OPTIONAL_JSON)

        dtos.forEach {
            val violations = validator.validate(it)
            assertTrue(violations.isEmpty())
        }
    }
}