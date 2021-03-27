package kr.s10th24b.app.githubrepoviewer

import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_search_history.*
import kotlinx.android.synthetic.main.searchhistory_recycler.*

class SearchHistoryActivity : AppCompatActivity() {
    lateinit var sqliteHelper: SqliteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_history)
        sqliteHelper = SqliteHelper(this,"searchhistory",1)
        val adapter = SearchHistoryRecyclerViewAdapter(sqliteHelper)
        SearchHistoryRecyclerViewAdapter.searchHistoryItems = loadData(sqliteHelper)
        recyclerSearchHistory.adapter = adapter
        recyclerSearchHistory.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        SearchHistoryRecyclerViewAdapter.searchHistoryItems.clear()
        SearchHistoryRecyclerViewAdapter.searchHistoryItems.addAll(sqliteHelper.selectSearchHistory())
        SearchHistoryRecyclerViewAdapter.adapter.notifyDataSetChanged()
        super.onResume()
    }

    fun loadData(sqliteHelper: SqliteHelper): MutableList<SearchHistory> {
        return sqliteHelper.selectSearchHistory()
    }
}