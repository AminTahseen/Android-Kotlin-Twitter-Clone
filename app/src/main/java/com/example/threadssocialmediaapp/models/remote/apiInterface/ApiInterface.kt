package com.example.threadssocialmediaapp.models.remote.apiInterface

import com.example.threadssocialmediaapp.models.dto.CommentsDTO
import com.example.threadssocialmediaapp.models.dto.PostDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("post")
    suspend fun getPosts(@Query("limit") limit: Int, @Query("page") page: Int): Response<PostDTO?>

    @GET("post/{id}")
    suspend fun getPost(@Path("id") postId: String): Response<PostDTO.Post?>

    @GET("post/{id}/comment")
    suspend fun getPostComments(
        @Path("id") postId: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<CommentsDTO?>
}