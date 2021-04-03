package kr.s10th24b.app.githubrepoviewer

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import kr.s10th24b.app.githubrepoviewer.databinding.ActivityListBinding
import kr.s10th24b.app.githubrepoviewer.databinding.ItemRecyclerBinding
import splitties.toast.toast


class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    lateinit var items: MutableList<RepositoryItem>
    private lateinit var mCompositeDisposable: CompositeDisposable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)



        mCompositeDisposable = CompositeDisposable()

        var progressObserver = PublishSubject.create<Boolean>()
        mCompositeDisposable.add(progressObserver
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("KHJ", "Observer knows it's done!")
                binding.searchRepoProgressBar.visibility = View.GONE
            })

        val githubService = GitHubService.create(progressObserver)

        var searchIn = "repository"
        var lastSearchIn = ""
        var searchText = ""
        var lastSearchText = ""
        var page = 1
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

        var recyclerViewAdapter = RepoRecylcerViewAdapter(applicationContext)
        binding.repoRecyclerView.apply {
            adapter = recyclerViewAdapter
            val linearLayout = LinearLayoutManager(context)
            layoutManager = linearLayout
            clearOnScrollListeners()
            addOnScrollListener(InfiniteScrollListener({
                loadRepoItems(
                    searchText,
                    searchIn,
                    githubService,
                    recyclerViewAdapter, items, ++page
                )
            }, linearLayout, binding.searchRepoProgressBar))
        }


        binding.searchRepoButton.setOnClickListener {
            items = mutableListOf()
            searchText = binding.searchRepoEditText.text.toString()
            if ((searchIn != lastSearchIn || searchText != lastSearchText) && searchText.isNotBlank() && searchText != "null") {
                lastSearchText = searchText
                lastSearchIn = searchIn
                page = 1
                binding.searchRepoProgressBar.visibility = View.VISIBLE
                ItemRecyclerBinding.inflate(
                    LayoutInflater.from(this),
                    binding.repoRecyclerView,
                    false
                ).repoItemProgressBar.visibility = View.VISIBLE
                binding.repoRecyclerView.smoothScrollBy(0, binding.repoRecyclerView.top)
//                toast("clicked $searchText")

                when (searchIn) {
                    "repository" -> {
                        var repoData =
                            loadRepoItems(
                                searchText,
                                searchIn,
                                githubService,
                                recyclerViewAdapter, items, page
                            )
                        recyclerViewAdapter.repoItems = repoData
                    }
                    "author" -> {
                        var repoData =
                            loadRepoItems(
                                searchText,
                                searchIn,
                                githubService,
                                recyclerViewAdapter, items, page
                            )
                        recyclerViewAdapter.repoItems = repoData
                    }
                    else -> {
                    }
                }
                var childCount = binding.repoRecyclerView.childCount
//                toast(childCount.toString())

                // save in search history
                val roomHelper = RoomHelper.getInstance(applicationContext)
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

    fun loadRepoItems(
        searchText: String,
        searchIn: String,
        gitHubService: GitHubService,
        adapter: RepoRecylcerViewAdapter,
        items: MutableList<RepositoryItem>,
        page: Int
    ): MutableList<RepositoryItem> {
        val repoObRepos: Observable<SearchRepositories>
        val authorObRepos: Observable<List<RepositoryItem>>
        when (searchIn) {
            "repository" -> {
                repoObRepos = gitHubService.getObRepos(searchText, page)
                mCompositeDisposable.add(
                    repoObRepos.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            val body = it.items
                            if (!body.isNullOrEmpty()) {
                                items.addAll(body)
                            } else {
                                toast("No Result!")
                            }
                            adapter.notifyDataSetChanged()
                        }, { Log.d("KHJ", "Error!") })
                )
            }
            "author" -> {
                authorObRepos = gitHubService.getObAuthors(searchText)
                mCompositeDisposable.add(
                    authorObRepos.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            if (!it.isNullOrEmpty()) {
                                items.addAll(it as List<RepositoryItem>)
                            } else toast("No Result!")
                            adapter.notifyDataSetChanged()
                        }, { Log.d("KHJ", "Error!") })
                )
            }
            else -> {
                authorObRepos = gitHubService.getObAuthors(searchText)
                mCompositeDisposable.add(
                    authorObRepos.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            if (!it.isNullOrEmpty()) {
                                items.addAll(it as List<RepositoryItem>)
                            } else toast("No Result!")
                            adapter.notifyDataSetChanged()
                        }, { Log.d("KHJ", "Error!") })
                )
            }
        }
        return items
    }

    override fun onDestroy() {
        mCompositeDisposable.dispose()
        toast("ListActivity Destroyed!")
        super.onDestroy()
    }
}