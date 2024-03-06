package com.example.firstviewsactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.firstviewsactivity.databinding.ActivitySpinnerBinding

@Suppress("DEPRECATION")
class SpinnerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpinnerBinding

    private var prevUrl : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val game = intent.getSerializableExtra("object") as Game
        val thumbnail = game.thumbnail
        Glide.with(this).load(thumbnail).into(binding.imageView)

        val url = game.gameUrl

        binding.gotosite.setOnClickListener{
            loadWebActivity(url)
        }

    }

    // enables button visibility if url exists others disables the button visibility
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val url = result.data?.getStringExtra("url")
            if (url != null){
                prevUrl = url
                binding.gotosite.visibility = View.VISIBLE
            } else{
                binding.gotosite.visibility = View.INVISIBLE
            }
        }
    }

    // loads the web activity for the selected object
    private fun loadWebActivity(url : String){
        val intent = Intent(this, WebActivity::class.java)
        intent.putExtra("url", url)
        resultLauncher.launch(intent)
    }
}