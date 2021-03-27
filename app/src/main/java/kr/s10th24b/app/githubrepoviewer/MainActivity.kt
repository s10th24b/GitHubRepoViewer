package kr.s10th24b.app.githubrepoviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        githubImageView.setOnClickListener {
            Toast.makeText(applicationContext,"Welcome to GithubRepoViewer",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,ListActivity::class.java)
            intent.putExtra("name","s10th24b")
            startActivity(intent)
        }
        bys10th24bTextview.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}