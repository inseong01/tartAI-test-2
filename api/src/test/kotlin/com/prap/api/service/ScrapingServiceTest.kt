package com.prap.api.python

import com.prap.api.dto.ArticleDto
import com.prap.api.dto.AuthorDto
import com.prap.api.dto.CategoryDto
import com.prap.api.service.ScrapingService

import com.fasterxml.jackson.databind.ObjectMapper
import com.prap.api.exception.CustomException
import com.prap.api.exception.ErrorCode
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.lang.RuntimeException
import kotlin.test.assertEquals

class ScrapingServiceTest {

    @Test
    fun `올바른 기사 목록을 반환한다`() {
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
        val fakeRunner = FakePythonRunner(CORRECT_JSON_FORMAT)
        val mockExecutor = PythonExecutor(fakeRunner, ObjectMapper())

        val service = ScrapingService(mockExecutor)
        val articles = service.getArticles()

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
        assertEquals(1, articles.size)
        assertEquals(excepted, articles)
    }

    @Test
    fun `서비스 호출에서 오류가 발생하면 CustomException 오류를 내보낸다`() {
        val fakeRunner = FakePythonRunner(
            result = "",
            exception = RuntimeException("Python failed")
        )
        val executor = PythonExecutor(fakeRunner, ObjectMapper())

        val service = ScrapingService(executor)

        val exception = assertThrows<CustomException> {
            service.getArticles()
        }

        assertEquals(ErrorCode.SERVICE_ERROR.message, exception.message)
    }

    @Test
    fun `데이터 검증에서 오류가 발생하면 CustomException 오류를 내보낸다`() {
        val EMPTY_TITLE_JSON_FORMAT = """
            [
                {
                    "id": "3018",
                    "url": "https://prap.kr/?p=3018",
                    "title": "",
                    "thumbnail": "https://prap.kr/wp-content/uploads/2025/11/미스치프_썸네일.jpg-650x428.png",
                    "category": { "name": "프랩칼럼", "url": "https://prap.kr/?cat=5" },
                    "excerpt": "MSCHF의 〈King Solomon’s Baby〉로 만나는 새로운 소유와 가치의 세계! 대형 아기 조각상을 1,000조각으로 나누어 판매하며 소유 와 신뢰, 조각투자의 새로운 가능성을 제시한 흥미로운 프로젝트. 지금 확인해보세요!",
                    "author": { "name": "prap", "url": "https://prap.kr/?author=2" },
                    "published_date": "2025-11-17"
               }
            ]
        """.trimIndent()
        val fakeRunner = FakePythonRunner(EMPTY_TITLE_JSON_FORMAT)
        val executor = PythonExecutor(fakeRunner, ObjectMapper())

        val service = ScrapingService(executor)

        val exception = assertThrows<CustomException> {
            service.getArticles()
        }

        assertEquals(ErrorCode.SERVICE_ERROR.message, exception.message)
    }
}
