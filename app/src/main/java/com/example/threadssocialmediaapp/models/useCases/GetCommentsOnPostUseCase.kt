package com.example.threadssocialmediaapp.models.useCases

import android.util.Log
import com.example.threadssocialmediaapp.models.remote.dto.CommentsDTO
import com.example.threadssocialmediaapp.models.remote.dto.PostDTO
import com.example.threadssocialmediaapp.models.paginate.PaginateCommentsRequest
import com.example.threadssocialmediaapp.models.paginate.PaginateRequest
import com.example.threadssocialmediaapp.models.remote.repository.CommentsRepo
import com.example.threadssocialmediaapp.models.remote.repository.PostsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCommentsOnPostUseCase @Inject constructor(
    private val commentsRepo: CommentsRepo
) {
    suspend operator fun invoke(params: PaginateCommentsRequest): Flow<CommentsDTO?> {
        Log.d("commentRequest",params.toString())
        return commentsRepo.getCommentsOnPost(params)
    }
}