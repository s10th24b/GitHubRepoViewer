package kr.s10th24b.app.githubrepoviewer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_author.*
import kotlinx.android.synthetic.main.fragment_repo.*

/**
 * A simple [Fragment] subclass.
 * Use the [RepoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RepoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            repoFragmentTextView.text = it.getString("pageType")
//            repoFragmentTextView.text = "hello repository"
            Toast.makeText(context,it.getString("pageType"),Toast.LENGTH_SHORT).show()
        }
//        Toast.makeText(context,repoFragmentTextView.text.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("KHJ","RepoFragment onDestroy!")
    }

}