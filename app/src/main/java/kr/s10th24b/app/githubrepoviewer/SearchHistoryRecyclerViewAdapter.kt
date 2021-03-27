package kr.s10th24b.app.githubrepoviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.searchhistory_recycler.view.*
import java.text.SimpleDateFormat

class SearchHistoryRecyclerViewAdapter(sqlite_Helper: SqliteHelper) :
    RecyclerView.Adapter<SearchHistoryRecyclerViewAdapter.SearchHistoryViewHolder>() {
    companion object {
        var searchHistoryItems = mutableListOf<SearchHistory>()
        lateinit var adapter: RecyclerView.Adapter<SearchHistoryViewHolder>
        lateinit var sqliteHelper: SqliteHelper
    }
    init {
        sqliteHelper = sqlite_Helper
        adapter = this
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
        lateinit var mSearchHistory: SearchHistory
        init {
            itemView.searchHistoryDelButton.setOnClickListener {
                sqliteHelper.deleteSearchHistory(mSearchHistory)
                searchHistoryItems.remove(mSearchHistory)
                adapter.notifyDataSetChanged()
            }
        }

        fun setItem(searchHistory: SearchHistory) {
            itemView.searchHistoryNumberTextView.text = searchHistory.number.toString()
            itemView.searchHistorySearchTextView.text = searchHistory.search
            val sdf = SimpleDateFormat("yyyy/MM/dd hh:mm")
            itemView.searchHistoryDateTimeTextView.text = sdf.format(searchHistory.dateTime)
            mSearchHistory = searchHistory
        }
    }
}
