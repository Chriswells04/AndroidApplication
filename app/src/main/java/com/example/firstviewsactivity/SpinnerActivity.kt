package com.example.firstviewsactivity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.firstviewsactivity.databinding.ActivityMainBinding
import com.example.firstviewsactivity.databinding.ActivitySpinnerBinding

class SpinnerActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpinnerBinding
    private lateinit var mainBinding: ActivityMainBinding
    private val image = "https://www.freetogame.com/g/540/thumbnail.jpg"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpinnerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Glide.with(this).load(image).into(binding.imageView)

    }
}