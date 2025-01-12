package com.example.threadssocialmediaapp.models.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.threadssocialmediaapp.models.local.model.SearchHistoryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TwitterCloneDao {

    @Query("SELECT * FROM SearchHistoryItem ORDER BY id DESC LIMIT 6")
    fun getRecentSearchHistory(): Flow<List<SearchHistoryItem>?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToRecentSearchHistory(searchHistoryItem: SearchHistoryItem)

    @Query("SELECT COUNT(*) FROM SearchHistoryItem WHERE searchHistoryTag = :value AND searchedHistoryByUserId=:userId")
    suspend fun isValuePresent(value: String,userId:String): Int

    @Query("DELETE FROM SearchHistoryItem WHERE id = :id")
    suspend fun deleteById(id: Int)
}