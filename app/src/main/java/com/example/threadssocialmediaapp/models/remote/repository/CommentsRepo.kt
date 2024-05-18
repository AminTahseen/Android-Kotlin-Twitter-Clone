package com.example.threadssocialmediaapp.models.remote.repository

import com.example.threadssocialmediaapp.models.dto.CommentsDTO
import com.example.threadssocialmediaapp.models.dto.PostDTO
import com.example.threadssocialmediaapp.models.paginate.PaginateCommentsRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Path

interface CommentsRepo {
    suspend fun getCommentsOnPost(
        paginateCommentsRequest: PaginateCommentsRequest
    ): Flow<CommentsDTO?>


}