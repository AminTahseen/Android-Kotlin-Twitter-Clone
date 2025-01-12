package com.example.threadssocialmediaapp.models.useCases

import com.example.threadssocialmediaapp.models.local.repository.TwitterCloneRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteItemFromHistoryUseCase @Inject constructor(
    private val twitterCloneRepo: TwitterCloneRepo
) {
    suspend operator fun invoke(params: Int): Flow<Int> {

        return twitterCloneRepo.deleteItemFromSearchHistory(params)
    }
}