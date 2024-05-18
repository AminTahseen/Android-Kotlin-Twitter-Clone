package com.example.threadssocialmediaapp.models.remote.repository

import com.example.threadssocialmediaapp.models.remote.dto.PostDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Path
import retrofit2.http.Query

interface PostsRepo {
    suspend fun getPosts(
        limit: Int,
        page: Int
    ): Flow<PostDTO?>

    suspend fun getPost(postId: String): Flow<PostDTO.Post?>
    suspend fun getPostsByTag(
        tag: String,
        limit: Int,
        page: Int
    ): Flow<PostDTO?>
}