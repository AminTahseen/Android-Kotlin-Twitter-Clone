package com.example.threadssocialmediaapp.models.useCases

import com.example.threadssocialmediaapp.models.local.model.SearchHistoryItem
import com.example.threadssocialmediaapp.models.local.repository.TwitterCloneRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToSearchHistoryUseCase @Inject constructor(
    private val twitterCloneRepo: TwitterCloneRepo
) {
    suspend operator fun invoke(params: SearchHistoryItem): Flow<Int> {

        return twitterCloneRepo.addToRecentSearchHistory(params)
    }
}