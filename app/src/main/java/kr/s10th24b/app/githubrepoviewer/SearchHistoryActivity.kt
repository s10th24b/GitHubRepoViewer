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
        roomHelper = RoomHelper.getInstance(this)
        val adapter = SearchHistoryRecyclerViewAdapter(roomHelper,this)
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