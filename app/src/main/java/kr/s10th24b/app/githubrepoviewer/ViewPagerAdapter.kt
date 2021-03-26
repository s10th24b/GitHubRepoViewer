package kr.s10th24b.app.githubrepoviewer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import kotlinx.android.synthetic.main.fragment_author.*
import kotlinx.android.synthetic.main.fragment_repo.*

class ViewPagerAdapter(fa: AppCompatActivity, repoItem: RepoItem) : FragmentStateAdapter(fa) {
    var fragments = mutableListOf<Fragment>()
    val item = repoItem
    override fun getItemCount() = fragments.size

    override fun containsItem(itemId: Long): Boolean {
        return super.containsItem(itemId) // 0 <= itemId < itemsize 만 체크한다.
    }

    override fun createFragment(position: Int): Fragment {
        Log.d("KHJ", "position: $position")
        when (position) {
            0 -> fragments[position].arguments = Bundle().apply { putString("pageType",item.title) }
            1 -> fragments[position].arguments = Bundle().apply { putString("pageType",item.author) }
        }
        return fragments[position]
    }

    fun setFragments(fragment: Fragment) {
        fragment.authorFragmentTextView.text = item.author
        fragment.repoFragmentTextView.text = item.title

    }

}