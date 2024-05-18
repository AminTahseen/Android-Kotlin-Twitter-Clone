package com.example.threadssocialmediaapp.models.paginate

data class PaginateCommentsRequest(
    val page: Int,
    val limit: Int,
    val postId: String,
)