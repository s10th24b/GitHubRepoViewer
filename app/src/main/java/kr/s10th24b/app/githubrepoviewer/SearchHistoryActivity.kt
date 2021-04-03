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
    lateinit var adapter: SearchHistoryRecyclerViewAdapter
    private lateinit var mCompositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        roomHelper = RoomHelper.getInstance(applicationContext)
        adapter = SearchHistoryRecyclerViewAdapter()
        adapter.searchHistoryItems = loadData(roomHelper)
        binding.recyclerSearchHistory.adapter = adapter
        mCompositeDisposable = CompositeDisposable()
        mCompositeDisposable.add(adapter.clickSubject.subscribe {
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("searchText", it)
            startActivity(intent)
        })
        mCompositeDisposable.add(adapter.itemRemovedSubject.subscribe {
            when (it.string) {
                "remove" -> {
                    roomHelper.roomSearchHistoryDao().delete(it.searchHistory)
                    adapter.notifyItemRemoved(adapter.searchHistoryItems.indexOf(it.searchHistory))
                    adapter.searchHistoryItems.remove(it.searchHistory)
                }
                else -> {

                }
            }
        })
        binding.recyclerSearchHistory.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onResume() {
        adapter.searchHistoryItems.clear()
        adapter.searchHistoryItems.addAll(roomHelper.roomSearchHistoryDao().getAll())
        adapter.notifyDataSetChanged()
        super.onResume()
    }

    override fun onDestroy() {
        Toast.makeText(this, "SearchHistoryActivity Destroyed!", Toast.LENGTH_SHORT).show()
        mCompositeDisposable.dispose()
        super.onDestroy()
    }

    private fun loadData(roomHelper: RoomHelper): MutableList<RoomSearchHistory> {
        return roomHelper.roomSearchHistoryDao().getAll()
    }
}