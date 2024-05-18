package com.example.threadssocialmediaapp.models.local.repositoryImpl

import com.example.threadssocialmediaapp.models.local.dao.TwitterCloneDao
import com.example.threadssocialmediaapp.models.local.model.SearchHistoryItem
import com.example.threadssocialmediaapp.models.local.repository.TwitterCloneRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TwitterCloneRepoImpl(private val twitterCloneDao: TwitterCloneDao) :
    TwitterCloneRepo {
    override fun getRecentSearchHistory(): Flow<List<SearchHistoryItem>?> {
        return twitterCloneDao.getRecentSearchHistory()
    }

    override suspend fun addToRecentSearchHistory(searchHistoryItem: SearchHistoryItem): Flow<Int> {
        val count = twitterCloneDao.isValuePresent(
            searchHistoryItem.searchHistoryTag,
            searchHistoryItem.searchedHistoryByUserId
        )
        if (count == 0) {
            twitterCloneDao.addToRecentSearchHistory(searchHistoryItem)
            return flow { emit(1) }
        } else {
            return flow { emit(0) }
        }
    }
}