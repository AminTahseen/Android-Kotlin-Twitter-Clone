package com.example.threadssocialmediaapp.models.remote.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostDTO(
    val data: List<Post>?,
    val limit: Int?,
    val page: Int?,
    val total: Int?,
) {
    @JsonClass(generateAdapter = true)
    data class Post(
        val id: String?,
        val image: String?,
        val likes: Int?,
        val owner: Owner?,
        val publishDate: String?,
        val tags: List<String>?,
        val text: String?
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