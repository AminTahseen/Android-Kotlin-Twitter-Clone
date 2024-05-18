package com.example.threadssocialmediaapp.models.remote.repository

import com.example.threadssocialmediaapp.models.dto.PostDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Path

interface PostsRepo {
    suspend fun getPosts(
        limit: Int,
        page: Int
    ): Flow<PostDTO?>

    suspend fun getPost(postId: String): Flow<PostDTO.Post?>

}