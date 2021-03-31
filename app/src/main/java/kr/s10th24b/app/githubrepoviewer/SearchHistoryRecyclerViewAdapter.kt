package kr.s10th24b.app.githubrepoviewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding4.widget.AdapterViewItemClickEvent
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kr.s10th24b.app.githubrepoviewer.databinding.SearchhistoryRecyclerBinding
import java.lang.Exception
import java.text.SimpleDateFormat

class SearchHistoryRecyclerViewAdapter(room_helper: RoomHelper) :
    RecyclerView.Adapter<SearchHistoryRecyclerViewAdapter.SearchHistoryViewHolder>() {
    var clickSubject: PublishSubject<String>

    companion object {
        var searchHistoryItems = mutableListOf<RoomSearchHistory>()
        lateinit var adapter: RecyclerView.Adapter<SearchHistoryViewHolder>
        lateinit var roomHelper: RoomHelper
    }

    init {
        roomHelper = room_helper
        adapter = this
        clickSubject = PublishSubject.create<String>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.searchhistory_recycler, parent, false)
        return SearchHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val searchHistoryItem = searchHistoryItems[position]
        holder.bind(searchHistoryItem,clickSubject,position)
    }

    override fun getItemCount() = searchHistoryItems.size

    class SearchHistoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var binding = SearchhistoryRecyclerBinding.bind(itemView)
        fun bind(searchHistory: RoomSearchHistory, clickSubject: PublishSubject<String>,position: Int) {
            binding.searchHistoryNumberTextView.text = searchHistory.no.toString()
            binding.searchHistorySearchTextView.text = searchHistory.search
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.searchHistoryDateTimeTextView.text = sdf.format(searchHistory.dateTime)
            itemView

            val mSearchHistory = searchHistory
            binding.searchHistoryDelButton.setOnClickListener {
                roomHelper.roomSearchHistoryDao().delete(mSearchHistory)
                searchHistoryItems.remove(mSearchHistory)
//                adapter.notifyDataSetChanged()
                adapter.notifyItemRemoved(position)
            }
            binding.searchHistorySearchTextView.setOnClickListener {
                clickSubject.onNext(binding.searchHistorySearchTextView.text.toString())
            }
        }
    }
}
