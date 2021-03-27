package kr.s10th24b.app.githubrepoviewer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import kr.s10th24b.app.githubrepoviewer.databinding.SettingsActivityBinding

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: SettingsActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SettingsActivityBinding.inflate(layoutInflater)
//        setContentView(R.layout.settings_activity)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        else {
            Toast.makeText(this,"savedInstanceState not null",Toast.LENGTH_SHORT).show()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val shared = PreferenceManager.getDefaultSharedPreferences(this)
        val darkmodeSwitch = shared.getBoolean("darkmode",true)
        if (darkmodeSwitch) {
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}