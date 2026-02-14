package com.prap.api.controller

import com.prap.api.dto.ArticleDto
import com.prap.api.dto.AuthorDto
import com.prap.api.dto.CategoryDto
import com.prap.api.service.ScrapingService

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.mockito.BDDMockito.given
import org.springframework.http.HttpStatus
import kotlin.test.Test

@WebMvcTest(ScrapingController::class)
class ScrapingControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var scrapingService: ScrapingService

    @Test
    fun `GET api articles 호출 시 200과 JSON 반환`() {

        val articles = listOf(
            ArticleDto(
                id = "1",
                url = "https://test.com",
                title = "테스트",
                thumbnail = "thumb",
                category = CategoryDto("카테고리 1", "카테고리 1 url"),
                excerpt = "요약",
                author = AuthorDto("작성자", "작성자 url"),
                published_date = "2025-01-01"
            )
        )

        given(scrapingService.getArticles()).willReturn(articles)

        mockMvc.perform(get("/api/articles"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").value("1"))
            .andExpect(jsonPath("$[0].url").value("https://test.com"))
            .andExpect(jsonPath("$[0].thumbnail").value("thumb"))
            .andExpect(jsonPath("$[0].category.name").value("카테고리 1"))
            .andExpect(jsonPath("$[0].category.url").value("카테고리 1 url"))
            .andExpect(jsonPath("$[0].excerpt").value("요약"))
            .andExpect(jsonPath("$[0].author.name").value("작성자"))
            .andExpect(jsonPath("$[0].author.url").value("작성자 url"))
            .andExpect(jsonPath("$[0].published_date").value("2025-01-01"))
    }

    @Test
    fun `서비스 예외 발생 시 ErrorResponseDto 반환`() {

        given(scrapingService.getArticles())
            .willThrow(RuntimeException("서비스 오류 발생"))

        mockMvc.perform(get("/api/articles"))
            .andExpect(status().isInternalServerError)
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.code").value(HttpStatus.INTERNAL_SERVER_ERROR.name))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.detail").value("서비스 오류 발생"))
            .andExpect(jsonPath("$.status").value(HttpStatus.INTERNAL_SERVER_ERROR.value()))
    }
}

