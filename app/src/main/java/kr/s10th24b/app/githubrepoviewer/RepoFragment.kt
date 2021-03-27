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
import kotlinx.android.synthetic.main.fragment_repo.view.*

/**
 * A simple [Fragment] subclass.
 * Use the [RepoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RepoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Toast.makeText(context,repoFragmentTextView.text.toString(),Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            Toast.makeText(context,it.getString("pageType"),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_repo, container, false)
        view.repoFragmentTextView.text = arguments?.getString("pageType")
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("KHJ","RepoFragment onDestroy!")
    }

}