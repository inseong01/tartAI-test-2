package com.prap.api.python

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.prap.api.dto.ArticleDto
import com.prap.api.dto.AuthorDto
import com.prap.api.dto.CategoryDto
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class PythonExtractorTest {
    @Test
    fun `올바른 JSON 형식을 내보낸다`() {
        val SUCCESS_JSON_FORMAT = """
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
        val executor = PythonExecutor(
            FakePythonRunner(SUCCESS_JSON_FORMAT),
            jacksonObjectMapper()
        )
        val result = executor.runScraper()

        val excepted = listOf<ArticleDto>(
            ArticleDto(
                id = "3018",
                url = "https://prap.kr/?p=3018",
                title = "솔로몬 재판이 실화로? 아기 잘라 나누기",
                thumbnail = "https://prap.kr/wp-content/uploads/2025/11/미스치프_썸네일.jpg-650x428.png",
                category = CategoryDto(name = "프랩칼럼", url = "https://prap.kr/?cat=5"),
                excerpt = "MSCHF의 〈King Solomon’s Baby〉로 만나는 새로운 소유와 가치의 세계! 대형 아기 조각상을 1,000조각으로 나누어 판매하며 소유 와 신뢰, 조각투자의 새로운 가능성을 제시한 흥미로운 프로젝트. 지금 확인해보세요!",
                author = AuthorDto(name = "prap", url = "https://prap.kr/?author=2"),
                published_date = "2025-11-17"
            )
        )

        assertEquals(1, result.size)
        assertEquals(excepted, result)
    }

    @Test
    fun `잘못된 JSON 형식이면 런타임 오류가 발생한다`() {
        val WRONG_JSON_FORMAT = """
            [
                {
                    "say": "Hi"
                }
            ]
        """.trimIndent()
        val executor = PythonExecutor(
            FakePythonRunner(WRONG_JSON_FORMAT),
            jacksonObjectMapper()
        )

        assertThrows<RuntimeException> {
            executor.runScraper()
        }

    }
}