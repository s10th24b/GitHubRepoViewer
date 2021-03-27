package kr.s10th24b.app.githubrepoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kr.s10th24b.app.githubrepoviewer.databinding.ActivitySearchHistoryBinding

class SearchHistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchHistoryBinding
    lateinit var roomHelper: RoomHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_search_history)
        binding = ActivitySearchHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        roomHelper = RoomHelper.getInstance(this)
        val adapter = SearchHistoryRecyclerViewAdapter(roomHelper,this)
        SearchHistoryRecyclerViewAdapter.searchHistoryItems = loadData(roomHelper)
        binding.recyclerSearchHistory.adapter = adapter
        binding.recyclerSearchHistory.layoutManager = LinearLayoutManager(this)
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