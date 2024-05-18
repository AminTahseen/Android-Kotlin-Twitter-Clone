package com.example.threadssocialmediaapp.models.remote.repositoryImpl

import android.util.Log
import com.example.threadssocialmediaapp.models.remote.dto.PostDTO
import com.example.threadssocialmediaapp.models.remote.apiInterface.ApiInterface
import com.example.threadssocialmediaapp.models.remote.repository.PostsRepo
import com.example.threadssocialmediaapp.utils.handleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostsRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : PostsRepo {
    override suspend fun getPosts(limit: Int, page: Int): Flow<PostDTO?> {
        return try {
            val response = apiInterface.getPosts(limit, page)
            val postDTOFlow: Flow<PostDTO?> = handleResponse(response)
            return postDTOFlow
        } catch (e: Exception) {
            Log.e("NetworkError", "${e.message}")
            flow { emit(null) }
        }
    }

    override suspend fun getPost(postId: String): Flow<PostDTO.Post?> {
        return try {
            val response = apiInterface.getPost(postId)
            val postFlow: Flow<PostDTO.Post?> = handleResponse(response)
            return postFlow
        } catch (e: Exception) {
            Log.e("NetworkError", "${e.message}")
            flow { emit(null) }
        }
    }

    override suspend fun getPostsByTag(tag: String, limit: Int, page: Int): Flow<PostDTO?> {
        return try {
            val response = apiInterface.getPostsByTag(tag, limit, page)
            val postDTOFlow: Flow<PostDTO?> = handleResponse(response)
            return postDTOFlow
        } catch (e: Exception) {
            Log.e("NetworkError", "${e.message}")
            flow { emit(null) }
        }
    }
}