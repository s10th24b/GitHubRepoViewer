package kr.s10th24b.app.githubrepoviewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kr.s10th24b.app.githubrepoviewer.databinding.SearchhistoryRecyclerBinding
import java.text.SimpleDateFormat

class SearchHistoryRecyclerViewAdapter(room_helper: RoomHelper) :
    RecyclerView.Adapter<SearchHistoryRecyclerViewAdapter.SearchHistoryViewHolder>() {
    companion object {
        val clickSubject = PublishSubject.create<String>()
        val clickEvent: Observable<String> = clickSubject
        var searchHistoryItems = mutableListOf<RoomSearchHistory>()
        lateinit var adapter: RecyclerView.Adapter<SearchHistoryViewHolder>
        lateinit var roomHelper: RoomHelper
    }
    init {
        roomHelper = room_helper
        adapter = this

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.searchhistory_recycler, parent, false)
        return SearchHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val searchHistoryItem = searchHistoryItems[position]
        holder.bind(searchHistoryItem)
    }

    override fun getItemCount() = searchHistoryItems.size

    class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: SearchhistoryRecyclerBinding
        lateinit var mSearchHistory: RoomSearchHistory
        init {
            binding = SearchhistoryRecyclerBinding.bind(itemView)
            binding.searchHistoryDelButton.setOnClickListener {
                roomHelper.roomSearchHistoryDao().delete(mSearchHistory)
                searchHistoryItems.remove(mSearchHistory)
                adapter.notifyDataSetChanged()
            }
//            binding.searchHistorySearchTextView.setOnClickListener {
//                val intent = Intent(itemView.context,ListActivity::class.java)
//                intent.putExtra("searchText",binding.searchHistorySearchTextView.text.toString())
//                context.startActivity(intent)
//            }
            binding.searchHistorySearchTextView.setOnClickListener {
                clickSubject.onNext(binding.searchHistorySearchTextView.text.toString())
            }
        }

        fun bind(searchHistory: RoomSearchHistory) {
            binding.searchHistoryNumberTextView.text = searchHistory.no.toString()
            binding.searchHistorySearchTextView.text = searchHistory.search
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.searchHistoryDateTimeTextView.text = sdf.format(searchHistory.dateTime)
            mSearchHistory = searchHistory
        }
    }
}
