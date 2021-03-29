package kr.s10th24b.app.githubrepoviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.leakcanary.LeakCanary
import kr.s10th24b.app.githubrepoviewer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        LeakCanary.install(application)
//        setContentView(R.layout.activity_main)
        binding.githubImageView.setOnClickListener {
            val intent = Intent(this,ListActivity::class.java)
//            intent.putExtra("name","s10th24b")
            startActivity(intent)
        }
        binding.settingsImageView.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.historyImageview.setOnClickListener {
            val intent = Intent(this,SearchHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}