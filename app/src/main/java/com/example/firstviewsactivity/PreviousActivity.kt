package com.example.firstviewsactivity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstviewsactivity.databinding.ActivityPreviousBinding

class PreviousActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviousBinding

    private lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var adapter : RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviousBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        layoutManager = LinearLayoutManager(this)
        binding.historyrv.layoutManager = layoutManager
        adapter = RecyclerAdapter()
        binding.historyrv.adapter = adapter

    }
}