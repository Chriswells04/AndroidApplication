package com.example.firstviewsactivity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
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
        val db = DataManager(this)
        layoutManager = LinearLayoutManager(this)

        binding.historyrv.layoutManager = layoutManager
        adapter = RecyclerAdapter()

        adapter.updateListOfGames(db.allGames())
        binding.historyrv.adapter = adapter

        handleDragging()

    }

    private fun handleDragging() {
        val dragCallBack = DragCallBack()
        val touchHelper = ItemTouchHelper(dragCallBack)
        touchHelper.attachToRecyclerView(binding.historyrv)
    }


        inner class DragCallBack : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            return makeMovementFlags(dragFlags, 0)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            adapter.notifyItemMoved(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }
    }
}