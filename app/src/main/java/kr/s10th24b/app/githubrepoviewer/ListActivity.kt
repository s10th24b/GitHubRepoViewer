package kr.s10th24b.app.githubrepoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

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


//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        searchRepoButton.setOnClickListener {
            val searchText = searchRepoEditText.text.toString()
            if (searchText.isNotBlank() && searchText != "null") {
                Toast.makeText(this, "clicked $searchText", Toast.LENGTH_SHORT).show()
                when (searchIn) {
                    "repository" -> {

                    }
                    "author" -> {

                    }
                    else -> {

                    }
                }

                // save in search history
                val roomHelper = RoomHelper.getInstance(this)
                val mSearchHistory = RoomSearchHistory(
                    searchRepoEditText.text.toString(),
                    System.currentTimeMillis()
                )
                roomHelper.roomSearchHistoryDao().insert(mSearchHistory)
            }
        }

        var s = intent.getStringExtra("searchText")
        if (s == null) s = "null"
        else {
            searchRepoEditText.setText(s)
            searchRepoButton.performClick()
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