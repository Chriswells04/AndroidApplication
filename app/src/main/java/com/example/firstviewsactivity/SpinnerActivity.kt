package com.example.firstviewsactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.firstviewsactivity.databinding.ActivitySpinnerBinding

class SpinnerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpinnerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val game = intent.getSerializableExtra("object") as Game
        val thumbnail = game.thumbnail
        Glide.with(this).load(thumbnail).into(binding.imageView)

    }
}