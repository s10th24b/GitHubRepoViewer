package kr.s10th24b.app.githubrepoviewer

import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_search_history.*
import kotlinx.android.synthetic.main.searchhistory_recycler.*

class SearchHistoryActivity : AppCompatActivity() {
    lateinit var roomHelper: RoomHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_history)
        roomHelper = Room.databaseBuilder(this, RoomHelper::class.java, "room_search_history") // 3번째가 DB 이름.
            .allowMainThreadQueries() // 이 옵션이 적용되지 않으면 앱이 동작을 멈춘다.
            // Room 은 기본적으로 서브스레드에서 동작하도록 설계되어 있기 때문.
            .build()
        val adapter = SearchHistoryRecyclerViewAdapter(roomHelper)
        SearchHistoryRecyclerViewAdapter.searchHistoryItems = loadData(roomHelper)
        recyclerSearchHistory.adapter = adapter
        recyclerSearchHistory.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        SearchHistoryRecyclerViewAdapter.searchHistoryItems.clear()
        SearchHistoryRecyclerViewAdapter.searchHistoryItems.addAll(roomHelper.roomSearchHistoryDao().getAll())
        SearchHistoryRecyclerViewAdapter.adapter.notifyDataSetChanged()
        super.onResume()
    }

    fun loadData(roomHelper: RoomHelper): MutableList<RoomSearchHistory> {
        return roomHelper.roomSearchHistoryDao().getAll()
    }
}