package kr.s10th24b.app.githubrepoviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.rxjava3.subjects.PublishSubject
import kr.s10th24b.app.githubrepoviewer.databinding.SearchhistoryRecyclerBinding
import java.text.SimpleDateFormat

class SearchHistoryRecyclerViewAdapter(room_helper: RoomHelper) :
    RecyclerView.Adapter<SearchHistoryRecyclerViewAdapter.SearchHistoryViewHolder>() {
    var searchHistoryItems = mutableListOf<RoomSearchHistory>()
    var clickSubject: PublishSubject<String>
    var itemRemovedSubject: PublishSubject<SearchHistoryForSubject>
    var roomHelper: RoomHelper

    init {
        roomHelper = room_helper
        clickSubject = PublishSubject.create()
        itemRemovedSubject = PublishSubject.create()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.searchhistory_recycler, parent, false)
        return SearchHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val searchHistoryItem = searchHistoryItems[position]
        holder.bind(searchHistoryItem, position, clickSubject, itemRemovedSubject)
    }

    override fun getItemCount() = searchHistoryItems.size

    class SearchHistoryViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var binding = SearchhistoryRecyclerBinding.bind(itemView)
        fun bind(
            searchHistory: RoomSearchHistory,
            position: Int,
            clickSubject: PublishSubject<String>,
            itemRemovedSubject: PublishSubject<SearchHistoryForSubject>
        ) {
            binding.searchHistoryNumberTextView.text = searchHistory.no.toString()
            binding.searchHistorySearchTextView.text = searchHistory.search
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            binding.searchHistoryDateTimeTextView.text = sdf.format(searchHistory.dateTime)
            itemView

            binding.searchHistoryDelButton.setOnClickListener {
                itemRemovedSubject.onNext(SearchHistoryForSubject("remove",position,searchHistory))
            }
            binding.searchHistorySearchTextView.setOnClickListener {
                clickSubject.onNext(binding.searchHistorySearchTextView.text.toString())
            }
        }
    }

    data class SearchHistoryForSubject(val string: String, val position: Int, val searchHistory: RoomSearchHistory)
}
