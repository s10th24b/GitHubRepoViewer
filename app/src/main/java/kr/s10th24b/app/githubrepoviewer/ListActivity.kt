package kr.s10th24b.app.githubrepoviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        var name = intent.getStringExtra("name").toString()
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show()
        searchRepoButton.setOnClickListener {
            val repoName = searchRepoEditText.text.toString()

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