package com.prap.api.controller

import com.prap.api.dto.ArticleDto
import com.prap.api.service.ScrapingService

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/articles")
class ScrapingController(
    private val scrapingService: ScrapingService,
) {

    @GetMapping
    fun scrapArticles(): ResponseEntity<List<ArticleDto>> {
        val articles = scrapingService.getArticles()
        return ResponseEntity.ok(articles)
    }
}