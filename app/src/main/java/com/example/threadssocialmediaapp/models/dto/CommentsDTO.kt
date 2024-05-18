package com.example.threadssocialmediaapp.models.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentsDTO(
    val data: List<Comment>?,
    val limit: Int?,
    val page: Int?,
    val total: Int?
) {
    @JsonClass(generateAdapter = true)
    data class Comment(
        val id: String?,
        val message: String?,
        val owner: Owner?,
        val post: String?,
        val publishDate: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Owner(
            val firstName: String?,
            val id: String?,
            val lastName: String?,
            val picture: String?,
            val title: String?
        )
    }
}