package kr.s10th24b.app.githubrepoviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import kr.s10th24b.app.githubrepoviewer.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_list)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var recyclerViewAdapter = RepoRecylcerViewAdapter(this)
        binding.repoRecyclerView.adapter = recyclerViewAdapter
        binding.repoRecyclerView.layoutManager = LinearLayoutManager(this)
        var searchIn = "repository"
        var searchText = ""
        binding.repoRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.repoRadioRepo -> {
                    binding.searchRepoEditText.hint = "repository name"
                    searchIn = "repository"
                }
                R.id.repoRadioAuthor -> {
                    binding.searchRepoEditText.hint = "author name"
                    searchIn = "author"
                }
                else -> {
                    binding.searchRepoEditText.hint = "author name"
                    searchIn = "author"
                }
            }
        }


//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
        binding.searchRepoButton.setOnClickListener {
            searchText = binding.searchRepoEditText.text.toString()
            if (searchText.isNotBlank() && searchText != "null") {
                Toast.makeText(this, "clicked $searchText", Toast.LENGTH_SHORT).show()
                when (searchIn) {
                    "repository" -> {
                        var repoData = loadRepoItems(searchText,searchIn)
                        recyclerViewAdapter.repoItems = repoData

                    }
                    "author" -> {
                        /////////////////
//                        val intent = Intent(this, NetworkHTTPConnectionTestActivity::class.java)
//                        intent.putExtra("query",searchText)
//                        startActivity(intent)
                        /////////////////
                    }
                    else -> {

                    }
                }

                // save in search history
                val roomHelper = RoomHelper.getInstance(this)
                val mSearchHistory = RoomSearchHistory(
                    binding.searchRepoEditText.text.toString(),
                    System.currentTimeMillis()
                )
                roomHelper.roomSearchHistoryDao().insert(mSearchHistory)
            }
        }

        var s = intent.getStringExtra("searchText")
        if (s == null) s = "null"
        else {
            binding.searchRepoEditText.setText(s)
            binding.searchRepoButton.performClick()
        }

    }

    fun loadRepoItems(query: String, searchIn: String): MutableList<RepositoryItem> {
        val items = mutableListOf<RepositoryItem>()

//        return MutableList(10) { RepoItem("", "Sample Repo Name${it + 1}", "Author${it + 1}") }
        return items
    }
}