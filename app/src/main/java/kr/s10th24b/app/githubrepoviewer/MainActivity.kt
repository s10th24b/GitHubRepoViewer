package kr.s10th24b.app.githubrepoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        githubImageView.setOnClickListener {
            Toast.makeText(applicationContext,"Welcome to GithubRepoViewer",Toast.LENGTH_SHORT).show()
        }
    }
}