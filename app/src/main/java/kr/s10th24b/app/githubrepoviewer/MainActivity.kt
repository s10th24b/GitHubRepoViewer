package kr.s10th24b.app.githubrepoviewer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.squareup.leakcanary.LeakCanary
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import kr.s10th24b.app.githubrepoviewer.databinding.ActivityMainBinding
import splitties.toast.toast

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var leakCanary = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (!leakCanary) {
            LeakCanary.install(application)
            leakCanary = true
        }
//        setContentView(R.layout.activity_main)
        binding.githubImageView.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
//            intent.putExtra("name","s10th24b")
            startActivity(intent)
        }
        binding.settingsImageView.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.historyImageview.setOnClickListener {
            val intent = Intent(this, SearchHistoryActivity::class.java)
            startActivity(intent)
        }
    }
}