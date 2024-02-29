package com.example.firstviewsactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import com.example.firstviewsactivity.databinding.ActivitySpinnerBinding
import com.example.firstviewsactivity.databinding.ActivityWebBinding


class webActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val url = intent.getStringExtra("url")
        binding.webView.webViewClient = WebViewClient() // prevents opening in browser app
        binding.webView.settings.javaScriptEnabled = true // required for search functionality in certain sites
        binding.webView.loadUrl(url!!)

        onBackPressedDispatcher.addCallback(this, backPressedCallback)
    }

    private val backPressedCallback = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            val intent = Intent()
            intent.putExtra("url", binding.webView.url)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}