package com.example.threadssocialmediaapp.models.useCases

import com.example.threadssocialmediaapp.models.dto.PostDTO
import com.example.threadssocialmediaapp.models.paginate.PaginateRequest
import com.example.threadssocialmediaapp.models.remote.repository.PostsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostByIdUseCase @Inject constructor(
    private val postsRepo: PostsRepo
) {
    suspend operator fun invoke(params: String): Flow<PostDTO.Post?> {
        return postsRepo.getPost(params)
    }
}