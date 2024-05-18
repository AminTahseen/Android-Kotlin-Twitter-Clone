package com.example.threadssocialmediaapp.models.paginate

data class PaginatePostTagRequest(
    val page: Int,
    val limit: Int,
    val tag: String
)