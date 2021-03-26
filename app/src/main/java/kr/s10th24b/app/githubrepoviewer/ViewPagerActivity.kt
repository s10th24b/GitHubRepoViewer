package kr.s10th24b.app.githubrepoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_view_pager.*

class ViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        var image = intent.getStringExtra("image").toString()
        var repositoryName = intent.getStringExtra("repository").toString()
        var author = intent.getStringExtra("author").toString()
        var fragmentsList = loadData()
        var viewPagerAdapter = ViewPagerAdapter(this, RepoItem(image, repositoryName, author))
        viewPagerAdapter.fragments = fragmentsList
        viewPager.adapter = viewPagerAdapter
        val tabLayout = repoAndAuthorTabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Repository"
                1 -> "Author"
                else -> "else"
            }
        }.attach()


    }


    fun loadData(): MutableList<Fragment> {
        val data = mutableListOf(RepoFragment(), AuthorFragment())
        return data
    }
}