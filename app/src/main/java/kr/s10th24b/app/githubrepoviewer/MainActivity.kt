package kr.s10th24b.app.githubrepoviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        githubImageView.setOnClickListener {
            val intent = Intent(this,ListActivity::class.java)
            intent.putExtra("name","s10th24b")
            startActivity(intent)
        }
        settingsImageView.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }
        historyImageview.setOnClickListener {
            val intent = Intent(this,SearchHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}