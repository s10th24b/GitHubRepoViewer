package kr.s10th24b.app.githubrepoviewer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RoomSearchHistory::class], version = 1, exportSchema = false)
abstract class RoomHelper : RoomDatabase() {
    companion object {
        private var INSTANCE: RoomHelper? = null
        fun getInstance(context: Context): RoomHelper { // Room Singleton Pattern
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(context, RoomHelper::class.java, "room_search_history")
                        .allowMainThreadQueries() // 이 옵션이 적용되지 않으면 앱이 멈춘다.
                        // Room 은 기본적으로 서브스레드에서 동작하도록 설게되어있기 때문.
                        // 실무에서는 지우도록.
                        .build()
            }
            return INSTANCE as RoomHelper
        }
    }

    abstract fun roomSearchHistoryDao(): RoomSearchHistoryDAO
}