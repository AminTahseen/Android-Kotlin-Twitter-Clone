package com.example.threadssocialmediaapp.models.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.threadssocialmediaapp.models.local.dao.TwitterCloneDao
import com.example.threadssocialmediaapp.models.local.model.SearchHistoryItem

@Database(
    entities = [SearchHistoryItem::class],
    version = 1
)
abstract class TwitterCloneDatabase : RoomDatabase() {
    abstract val twitterCloneDao: TwitterCloneDao

    companion object {
        const val DATABASE_NAME = "twitter_clone_db"
    }
}