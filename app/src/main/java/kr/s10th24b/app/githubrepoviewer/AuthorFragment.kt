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

class AuthorFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            authorFragmentTextView.text = it.getString("pageType")
//            authorFragmentTextView.text = "hello author"
            Toast.makeText(context, it.getString("pageType"), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_author, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("KHJ", "AuthorFragment onDestroy!")
    }
}