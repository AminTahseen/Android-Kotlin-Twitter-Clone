package com.example.threadssocialmediaapp.models.useCases

import com.example.threadssocialmediaapp.models.remote.dto.PostDTO
import com.example.threadssocialmediaapp.models.paginate.PaginatePostTagRequest
import com.example.threadssocialmediaapp.models.paginate.PaginateRequest
import com.example.threadssocialmediaapp.models.remote.repository.PostsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostsByTagUseCase @Inject constructor(
    private val postsRepo: PostsRepo
) {
    suspend operator fun invoke(params: PaginatePostTagRequest): Flow<PostDTO?> {
        return postsRepo.getPostsByTag(params.tag, params.limit, params.page)
    }
}