package kr.s10th24b.app.githubrepoviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kr.s10th24b.app.githubrepoviewer.databinding.ActivityNetworkHTTPConnectionTestBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import kotlin.text.StringBuilder

class NetworkHTTPConnectionTestActivity : AppCompatActivity() {
    lateinit var binding: ActivityNetworkHTTPConnectionTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_h_t_t_p_connection_test)
        binding = ActivityNetworkHTTPConnectionTestBinding.inflate(layoutInflater)
        val query = intent.getStringExtra("query")
        thread(start=true) {
            try {
                var urlText = query.toString()
                if (!urlText.startsWith("https")) urlText = "https://$query"
                val url = URL(urlText)
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    val streamReader = InputStreamReader(urlConnection.inputStream)
                    val buffered = BufferedReader(streamReader)
                    val content = StringBuilder()
                    while (true) {
                        val line = buffered.readLine() ?: break
                        content.append(line)
                    }
                    buffered.close()
                    urlConnection.disconnect()
                    runOnUiThread {
                        binding.httpTestTextView.text = content.toString()
                    }
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                Log.d("KHJ",e.toString())
            }
        }
    }
}