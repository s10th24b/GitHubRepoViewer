package kr.s10th24b.app.githubrepoviewer

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kr.s10th24b.app.githubrepoviewer.databinding.FragmentAuthorBinding
import kr.s10th24b.app.githubrepoviewer.databinding.FragmentRepoBinding
import java.io.File

class AuthorFragment : Fragment() {
    lateinit var binding: FragmentAuthorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sep = File.separator
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            Toast.makeText(context, it.getString("pageType"), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_author, container, false)
        binding = FragmentAuthorBinding.inflate(inflater, container, false)
//        view.authorFragmentTextView.text = arguments?.getString("pageType")
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("KHJ", "AuthorFragment onDestroy!")
    }
}