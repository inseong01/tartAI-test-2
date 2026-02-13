package com.prap.api.dto

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.validation.Validation
import kotlin.test.Test
import kotlin.test.assertTrue

class ArticleDtoValidationTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator
    private val mapper = jacksonObjectMapper()

    @Test
    fun `올바른 구조면 ArticleDto 검증을 통과한다`() {
        val CORRECT_JSON_FORMAT = """
            [
                {
                    "id": "3018",
                    "url": "https://prap.kr/?p=3018",
                    "title": "솔로몬 재판이 실화로? 아기 잘라 나누기",
                    "thumbnail": "https://prap.kr/wp-content/uploads/2025/11/미스치프_썸네일.jpg-650x428.png",
                    "category": { "name": "프랩칼럼", "url": "https://prap.kr/?cat=5" },
                    "excerpt": "MSCHF의 〈King Solomon’s Baby〉로 만나는 새로운 소유와 가치의 세계! 대형 아기 조각상을 1,000조각으로 나누어 판매하며 소유 와 신뢰, 조각투자의 새로운 가능성을 제시한 흥미로운 프로젝트. 지금 확인해보세요!",
                    "author": { "name": "prap", "url": "https://prap.kr/?author=2" },
                    "published_date": "2025-11-17"
               }
            ]
        """.trimIndent()

        val articles: List<ArticleDto> = mapper.readValue(CORRECT_JSON_FORMAT)

        articles.forEach {
            val violations = validator.validate(it)
            assertTrue(violations.isEmpty())
        }
    }

    @Test
    fun `올바르지 않은 구조면 ArticleDto 검증을 통과하지 못한다`() {
        val EMPTY_ID_JSON_FORMAT = """
            [
                {
                    "id": "",
                    "url": "https://prap.kr/?p=3018",
                    "title": "솔로몬 재판이 실화로? 아기 잘라 나누기",
                    "thumbnail": "https://prap.kr/wp-content/uploads/2025/11/미스치프_썸네일.jpg-650x428.png",
                    "category": { "name": "프랩칼럼", "url": "https://prap.kr/?cat=5" },
                    "excerpt": "MSCHF의 〈King Solomon’s Baby〉로 만나는 새로운 소유와 가치의 세계! 대형 아기 조각상을 1,000조각으로 나누어 판매하며 소유 와 신뢰, 조각투자의 새로운 가능성을 제시한 흥미로운 프로젝트. 지금 확인해보세요!",
                    "author": { "name": "prap", "url": "https://prap.kr/?author=2" },
                    "published_date": "2025-11-17"
               }
            ]
        """.trimIndent()

        val articles: List<ArticleDto> = mapper.readValue(EMPTY_ID_JSON_FORMAT)

        articles.forEach {
            val violations = validator.validate(it)
            assertTrue(violations.isNotEmpty())
        }
    }
}