package kr.s10th24b.app.githubrepoviewer

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper

@Database(entities = arrayOf(RoomSearchHistory::class), version = 1, exportSchema = false)
abstract class RoomHelper : RoomDatabase() {
    abstract fun roomSearchHistoryDao(): RoomSearchHistoryDAO
}