package com.example.threadssocialmediaapp.models.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SearchHistoryItem(
    val searchHistoryTag: String = "",
    val searchedHistoryDate: String = "",
    val searchedHistoryByUserId: String = "",
    @PrimaryKey val id: Int? = null
)
