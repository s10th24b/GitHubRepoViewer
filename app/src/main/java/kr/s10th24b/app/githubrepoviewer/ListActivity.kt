package kr.s10th24b.app.githubrepoviewer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import kr.s10th24b.app.githubrepoviewer.databinding.ActivityListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        var searchIn = "repository"
        var lastSearchIn = ""
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
        var searchText = ""
        var lastSearchText = ""
        binding.searchRepoButton.setOnClickListener {
            searchText = binding.searchRepoEditText.text.toString()
            if ((searchIn != lastSearchIn || searchText != lastSearchText) && searchText.isNotBlank() && searchText != "null") {
                lastSearchText = searchText
                lastSearchIn = searchIn
                binding.searchRepoProgressBar.visibility = View.VISIBLE
                Toast.makeText(this, "clicked $searchText", Toast.LENGTH_SHORT).show()
                when (searchIn) {
                    "repository" -> {
                        var repoData =
                            loadRepoItems(searchText, searchIn, retrofit, recyclerViewAdapter, this)
                        recyclerViewAdapter.repoItems = repoData

                    }
                    "author" -> {
                        var repoData =
                            loadRepoItems(searchText, searchIn, retrofit, recyclerViewAdapter, this)
                        recyclerViewAdapter.repoItems = repoData
                    }
                    else -> {

                    }
                }

                // save in search history
                val roomHelper = RoomHelper.getInstance(applicationContext)
                val mSearchHistory = RoomSearchHistory(
                    binding.searchRepoEditText.text.toString(),
                    System.currentTimeMillis()
                )
                roomHelper.roomSearchHistoryDao().insert(mSearchHistory)
            }
            binding.searchRepoProgressBar.visibility = View.GONE
        }

        var s = intent.getStringExtra("searchText")
        if (s == null) s = "null"
        else {
            binding.searchRepoEditText.setText(s)
            binding.searchRepoButton.performClick()
        }

    }

    fun loadRepoItems(
        searchText: String,
        searchIn: String,
        retrofit: Retrofit,
        adapter: RepoRecylcerViewAdapter,
        context: Context
    ): MutableList<RepositoryItem> {
        val items = mutableListOf<RepositoryItem>()
        val searchInCall: Call<List<RepositoryItem>>
        val repoRepos: Call<SearchRepositories>
        val authorRepos: Call<List<RepositoryItem>>
        val gitHubService = retrofit.create(GitHubService::class.java)
        when (searchIn) {
            "repository" -> {
                repoRepos = gitHubService.repos(searchText)
                repoRepos.enqueue(object : Callback<SearchRepositories> {
                    override fun onResponse(
                        call: Call<SearchRepositories>,
                        response: Response<SearchRepositories>
                    ) {
                        val body = response.body()?.items
//                        Toast.makeText(context,body.toString(),Toast.LENGTH_SHORT).show()
                        if (!body.isNullOrEmpty()) {
                            items.addAll(body)
                        } else {
                            Toast.makeText(context, "No Result!", Toast.LENGTH_SHORT).show()
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<SearchRepositories>, t: Throwable) {
                        Log.d("KHJ", "gitHubService onFailure!")
                    }
                })
            }
            "author" -> {
                authorRepos = gitHubService.authors(searchText)
                authorRepos.enqueue(object : Callback<List<RepositoryItem>> {
                    override fun onResponse(
                        call: Call<List<RepositoryItem>>,
                        response: Response<List<RepositoryItem>>
                    ) {
                        val body = response.body()
                        if (!body.isNullOrEmpty()) {
                            items.addAll(response.body() as List<RepositoryItem>)
                        } else {
                            Toast.makeText(context, "No result!", Toast.LENGTH_SHORT).show()
                        }
                        adapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<List<RepositoryItem>>, t: Throwable) {
                        Log.d("KHJ", "gitHubService onFailure!")
                    }
                })
            }
            else -> {
                searchInCall = gitHubService.authors(searchText)
                searchInCall.enqueue(object : Callback<List<RepositoryItem>> {
                    override fun onFailure(call: Call<List<RepositoryItem>>, t: Throwable) {
                        Log.d("KHJ", "gitHubService onFailure!")
                    }

                    override fun onResponse(
                        call: Call<List<RepositoryItem>>,
                        response: Response<List<RepositoryItem>>
                    ) {
                        items.addAll(response.body() as List<RepositoryItem>)
                        adapter.notifyDataSetChanged()
                    }
                })
            }
        }

//        return MutableList(10) { RepoItem("", "Sample Repo Name${it + 1}", "Author${it + 1}") }
        return items
    }
}