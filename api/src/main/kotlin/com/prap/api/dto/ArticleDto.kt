package com.prap.api.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank


data class CategoryDto(
    val name: String,
    val url: String
)

data class AuthorDto(
    val name: String,
    val url: String
)

data class ArticleDto(
    @field:NotBlank(message = "아이디는 필수입니다")
    val id: String,

    @field:NotBlank(message = "제목은 필수입니다")
    val title: String,

    @field:NotBlank(message = "URL은 필수입니다")
    val url: String,

    @field:NotBlank(message = "섬네일 URL은 필수입니다")
    val thumbnail: String,

    val category: CategoryDto,

    @field:NotBlank(message = "발췌는 필수입니다")
    val excerpt: String,

    val author: AuthorDto,

    @field:NotBlank(message = "발행일은 필수입니다")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val published_date: String,
)