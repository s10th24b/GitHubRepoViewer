package kr.s10th24b.app.githubrepoviewer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jakewharton.rxbinding4.view.clicks
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kr.s10th24b.app.githubrepoviewer.databinding.ActivityListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import splitties.toast.toast
import java.util.*
import java.util.concurrent.TimeUnit

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    lateinit var items: MutableList<RepositoryItem>
    lateinit var mCompositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_list)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var recyclerViewAdapter = RepoRecylcerViewAdapter(applicationContext)
        binding.repoRecyclerView.adapter = recyclerViewAdapter
        binding.repoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.repoRecyclerView.clearOnScrollListeners()
//        binding.repoRecyclerView.addOnScrollListener(InfiniteScrollListener({}))

        val githubService = GitHubService.create()
        mCompositeDisposable = CompositeDisposable()


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


        val subscribe = binding.searchRepoButton.clicks()
            .doOnNext { toast("doOnNext Thread: ${Thread.currentThread()}") }
            .doOnError { toast("doOnError Thread: ${Thread.currentThread()}") }
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toast(binding.searchRepoEditText.text)
                Log.d("KHJ", "${Thread.currentThread()}")
            }, { Log.d("KHJ", "${Thread.currentThread()}") })

        binding.searchRepoEditText.textChangeEvents()
            .debounce(100L, TimeUnit.MILLISECONDS)
            .filter { binding.searchRepoEditText.text.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toast(binding.searchRepoEditText.text)
                Log.d("KHJ", "${Thread.currentThread()}")
            }, { Log.d("KHJ", "${Thread.currentThread()}") })

        var searchText = ""
        var lastSearchText = ""
        binding.searchRepoButton.setOnClickListener {
            items = mutableListOf()
            searchText = binding.searchRepoEditText.text.toString()
            if ((searchIn != lastSearchIn || searchText != lastSearchText) && searchText.isNotBlank() && searchText != "null") {
                lastSearchText = searchText
                lastSearchIn = searchIn
                binding.searchRepoProgressBar.visibility = View.VISIBLE
//                toast("clicked $searchText")
                when (searchIn) {
                    "repository" -> {
                        var repoData =
                            loadRepoItems(
                                searchText,
                                searchIn,
                                githubService,
                                recyclerViewAdapter,
                                items
                            )
                        recyclerViewAdapter.repoItems = repoData

                    }
                    "author" -> {
                        var repoData =
                            loadRepoItems(
                                searchText,
                                searchIn,
                                githubService,
                                recyclerViewAdapter,
                                items
                            )
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
        gitHubService: GitHubService,
        adapter: RepoRecylcerViewAdapter,
        items: MutableList<RepositoryItem>
    ): MutableList<RepositoryItem> {
        val searchInCall: Call<List<RepositoryItem>>
        val repoCallRepos: Call<SearchRepositories>
        val repoObRepos: Observable<SearchRepositories>
        val authorCallRepos: Call<List<RepositoryItem>>
        val authorObRepos: Observable<List<RepositoryItem>>
        when (searchIn) {
//            "repositoryCall" -> {
//                repoCallRepos = gitHubService.getCallRepos(searchText, 1)
//                repoCallRepos.enqueue(object : Callback<SearchRepositories> {
//                    override fun onResponse(
//                        call: Call<SearchRepositories>,
//                        response: Response<SearchRepositories>
//                    ) {
//                        val body = response.body()?.items
//                        if (!body.isNullOrEmpty()) {
//                            items.addAll(body)
//                        } else {
//                            toast("No Result!")
//                        }
//                        adapter.notifyDataSetChanged()
//                    }
//
//                    override fun onFailure(call: Call<SearchRepositories>, t: Throwable) {
//                        Log.d("KHJ", "gitHubService repository onFailure!")
//                    }
//                })
//            }
            "repository" -> {
                repoObRepos = gitHubService.getObRepos(searchText, 1)
                mCompositeDisposable.add(
                    repoObRepos.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            val body = it.items
                            if (!body.isNullOrEmpty()) {
                                items.addAll(body)
                            } else {
                                toast("No Result!")
                            }
                            adapter.notifyDataSetChanged()
                        }
                )
            }

//            "authorCall" -> {
//                authorCallRepos = gitHubService.getCallAuthors(searchText)
//                authorCallRepos.enqueue(object : Callback<List<RepositoryItem>> {
//                    override fun onResponse(
//                        call: Call<List<RepositoryItem>>,
//                        response: Response<List<RepositoryItem>>
//                    ) {
//                        val body = response.body()
//                        if (!body.isNullOrEmpty()) {
//                            items.addAll(response.body() as List<RepositoryItem>)
//                        } else {
//                            toast("No Result!")
//                        }
//                        adapter.notifyDataSetChanged()
//                    }
//
//                    override fun onFailure(call: Call<List<RepositoryItem>>, t: Throwable) {
//                        Log.d("KHJ", "gitHubService authors onFailure!")
//                    }
//                })
//            }
            "author" -> {
                authorObRepos = gitHubService.getObAuthors(searchText)
                authorObRepos.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (!it.isNullOrEmpty()) {
                            items.addAll(it as List<RepositoryItem>)
                        }
                        else toast("No Result!")
                        adapter.notifyDataSetChanged()
                    }
            }
            else -> {
                authorObRepos = gitHubService.getObAuthors(searchText)
                authorObRepos.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (!it.isNullOrEmpty()) {
                            items.addAll(it as List<RepositoryItem>)
                        }
                        else toast("No Result!")
                        adapter.notifyDataSetChanged()
                    }
            }
        }

//        return MutableList(10) { RepoItem("", "Sample Repo Name${it + 1}", "Author${it + 1}") }
        return items
    }

    override fun onDestroy() {
        super.onDestroy()
        mCompositeDisposable.dispose()
        toast("ListActivity Destroyed!")
    }
}