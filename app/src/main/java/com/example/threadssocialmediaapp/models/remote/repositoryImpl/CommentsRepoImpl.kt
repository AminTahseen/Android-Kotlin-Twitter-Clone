package com.example.threadssocialmediaapp.models.remote.repositoryImpl

import android.util.Log
import com.example.threadssocialmediaapp.models.dto.CommentsDTO
import com.example.threadssocialmediaapp.models.paginate.PaginateCommentsRequest
import com.example.threadssocialmediaapp.models.remote.apiInterface.ApiInterface
import com.example.threadssocialmediaapp.models.remote.repository.CommentsRepo
import com.example.threadssocialmediaapp.utils.handleResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CommentsRepoImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : CommentsRepo {

    override suspend fun getCommentsOnPost(paginateCommentsRequest: PaginateCommentsRequest): Flow<CommentsDTO?> {
        return try {
            val response = apiInterface.getPostComments(
                page = paginateCommentsRequest.page,
                limit = paginateCommentsRequest.limit,
                postId = paginateCommentsRequest.postId
            )
            val commentsDTOFlow: Flow<CommentsDTO?> = handleResponse(response)
            return commentsDTOFlow
        } catch (e: Exception) {
            Log.e("NetworkError", "${e.message}")
            flow { emit(null) }
        }
    }

}