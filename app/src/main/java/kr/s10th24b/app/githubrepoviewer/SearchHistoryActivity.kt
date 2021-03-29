package kr.s10th24b.app.githubrepoviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import kr.s10th24b.app.githubrepoviewer.databinding.ActivitySearchHistoryBinding

class SearchHistoryActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchHistoryBinding
    lateinit var roomHelper: RoomHelper
    private lateinit var compositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_search_history)
        binding = ActivitySearchHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        roomHelper = RoomHelper.getInstance(this)
        val adapter = SearchHistoryRecyclerViewAdapter(roomHelper)
        SearchHistoryRecyclerViewAdapter.searchHistoryItems = loadData(roomHelper)
        val searchHistoryRecyclerViewAdapterViewHolder = SearchHistoryRecyclerViewAdapter.clickEvent.subscribe{
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("searchText", it)
            startActivity(intent)
        }
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(searchHistoryRecyclerViewAdapterViewHolder)
        binding.recyclerSearchHistory.adapter = adapter
        binding.recyclerSearchHistory.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        SearchHistoryRecyclerViewAdapter.searchHistoryItems.clear()
        SearchHistoryRecyclerViewAdapter.searchHistoryItems.addAll(
            roomHelper.roomSearchHistoryDao().getAll()
        )
        SearchHistoryRecyclerViewAdapter.adapter.notifyDataSetChanged()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "SearchHistoryActivity Destroyed!", Toast.LENGTH_SHORT).show()
        compositeDisposable.dispose()
    }

    fun loadData(roomHelper: RoomHelper): MutableList<RoomSearchHistory> {
        return roomHelper.roomSearchHistoryDao().getAll()
    }
}