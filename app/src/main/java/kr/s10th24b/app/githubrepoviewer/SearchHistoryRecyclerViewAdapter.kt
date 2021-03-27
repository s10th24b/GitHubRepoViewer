package kr.s10th24b.app.githubrepoviewer

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.searchhistory_recycler.view.*
import java.text.SimpleDateFormat

class SearchHistoryRecyclerViewAdapter(room_helper: RoomHelper, _context: Context) :
    RecyclerView.Adapter<SearchHistoryRecyclerViewAdapter.SearchHistoryViewHolder>() {
    companion object {
        var searchHistoryItems = mutableListOf<RoomSearchHistory>()
        lateinit var adapter: RecyclerView.Adapter<SearchHistoryViewHolder>
        lateinit var roomHelper: RoomHelper
        lateinit var context: Context
    }
    init {
        roomHelper = room_helper
        adapter = this
        context = _context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.searchhistory_recycler, parent, false)
        return SearchHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder, position: Int) {
        val searchHistoryItem = searchHistoryItems[position]
        holder.setItem(searchHistoryItem)
    }

    override fun getItemCount() = searchHistoryItems.size

    class SearchHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var mSearchHistory: RoomSearchHistory
        init {
            itemView.searchHistoryDelButton.setOnClickListener {
                roomHelper.roomSearchHistoryDao().delete(mSearchHistory)
                searchHistoryItems.remove(mSearchHistory)
                adapter.notifyDataSetChanged()
            }
            itemView.searchHistorySearchTextView.setOnClickListener {
                val intent = Intent(itemView.context,ListActivity::class.java)
                intent.putExtra("searchText",itemView.searchHistorySearchTextView.text.toString())
                context.startActivity(intent)
            }
        }

        fun setItem(searchHistory: RoomSearchHistory) {
            itemView.searchHistoryNumberTextView.text = searchHistory.no.toString()
            itemView.searchHistorySearchTextView.text = searchHistory.search
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            itemView.searchHistoryDateTimeTextView.text = sdf.format(searchHistory.dateTime)
            mSearchHistory = searchHistory
        }
    }
}
