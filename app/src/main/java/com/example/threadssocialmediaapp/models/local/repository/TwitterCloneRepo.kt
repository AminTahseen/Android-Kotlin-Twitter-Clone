package com.example.threadssocialmediaapp.models.local.repository

import com.example.threadssocialmediaapp.models.local.model.SearchHistoryItem
import kotlinx.coroutines.flow.Flow

interface TwitterCloneRepo {
    fun getRecentSearchHistory(): Flow<List<SearchHistoryItem>?>
    suspend fun addToRecentSearchHistory(searchHistoryItem: SearchHistoryItem):Flow<Int>
}