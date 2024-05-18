package com.example.threadssocialmediaapp.models.useCases

import com.example.threadssocialmediaapp.models.dto.PostDTO
import com.example.threadssocialmediaapp.models.paginate.PaginateRequest
import com.example.threadssocialmediaapp.models.remote.repository.PostsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val postsRepo: PostsRepo
) {
    suspend operator fun invoke(params: PaginateRequest): Flow<PostDTO?> {
        return postsRepo.getPosts(params.limit, params.page)
    }
}