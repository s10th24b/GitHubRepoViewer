package kr.s10th24b.app.githubrepoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import kr.s10th24b.app.githubrepoviewer.databinding.ActivityViewPagerBinding

class ViewPagerActivity : AppCompatActivity() {
    lateinit var binding: ActivityViewPagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_view_pager)
        binding = ActivityViewPagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var image = intent.getStringExtra("image").toString()
        var repositoryName = intent.getStringExtra("repository").toString()
        var author = intent.getStringExtra("author").toString()
        var fragmentsList = loadData()
        var viewPagerAdapter = ViewPagerAdapter(this, RepoItem(image, repositoryName, author))
        viewPagerAdapter.fragments = fragmentsList
        binding.viewPager.adapter = viewPagerAdapter
        val tabLayout = binding.repoAndAuthorTabLayout
        TabLayoutMediator(tabLayout, binding.viewPager) { tab, position ->
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