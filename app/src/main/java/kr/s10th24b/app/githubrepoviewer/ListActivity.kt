package kr.s10th24b.app.githubrepoviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_main.*

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        var name = intent.getStringExtra("name").toString()

        var searchIn = "repository"
        repoRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.repoRadioRepo -> {
                    searchRepoEditText.hint = "repository name"
                    searchIn = "repository"
                }
                R.id.repoRadioAuthor -> {
                    searchRepoEditText.hint = "author name"
                    searchIn = "author"
                }
                else -> {
                    searchRepoEditText.hint = "author name"
                    searchIn = "author"
                }
            }
        }


        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
        searchRepoButton.setOnClickListener {
            val searchText = searchRepoEditText.text.toString()
            when (searchIn) {
                "repository" -> {

                }
                "author" -> {

                }
                else -> {

                }
            }

            // save in search history
            val roomHelper = Room.databaseBuilder(this,RoomHelper::class.java,"room_search_history")
                .allowMainThreadQueries()
                .build()
            val mSearchHistory = RoomSearchHistory(searchRepoEditText.text.toString(),System.currentTimeMillis())
            roomHelper.roomSearchHistoryDao().insert(mSearchHistory)
        }
        var recyclerViewAdapter = RepoRecylcerViewAdapter()
        var repoData = loadRepoItems()
        recyclerViewAdapter.repoItems = repoData
        repoRecyclerView.adapter = recyclerViewAdapter
        repoRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun loadRepoItems(): MutableList<RepoItem> {
        return MutableList(10) { RepoItem("", "Sample Repo Name${it + 1}", "Author${it + 1}") }
    }
}