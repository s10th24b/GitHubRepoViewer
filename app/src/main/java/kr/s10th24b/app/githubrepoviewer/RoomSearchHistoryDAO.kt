package kr.s10th24b.app.githubrepoviewer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface RoomSearchHistoryDAO {
    @Query("select * from orm_search_history") //R
    fun getAll(): MutableList<RoomSearchHistory>

    @Insert(onConflict = REPLACE) // C, Update 까지
    fun insert(searchHistory: RoomSearchHistory)

    @Delete //D
    fun delete(searchHistory: RoomSearchHistory)
}