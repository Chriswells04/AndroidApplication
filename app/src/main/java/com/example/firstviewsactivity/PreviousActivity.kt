package com.example.firstviewsactivity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstviewsactivity.databinding.ActivityPreviousBinding


class PreviousActivity : AppCompatActivity()
{

    private lateinit var binding: ActivityPreviousBinding

    private lateinit var layoutManager: RecyclerView.LayoutManager

    private lateinit var adapter : RecyclerAdapter

    private lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviousBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        dataManager = DataManager(this)

        layoutManager = LinearLayoutManager(this)
        binding.historyrv.layoutManager = layoutManager
        adapter = RecyclerAdapter()

        adapter.updateListOfGames(dataManager.allGames())
        binding.historyrv.adapter = adapter

        handleDragging()

        // enables items that are swiped to be deleted from recyclerView
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.bindingAdapterPosition
                val game = adapter.list.removeAt(pos)
                dataManager.deleteGame(game)
                adapter.notifyItemRemoved(pos)
                Toast.makeText(
                    this@PreviousActivity,
                    "Game deleted: " + game.name,
                    Toast.LENGTH_SHORT
                ).show()
                refreshGames()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.historyrv)
    }

    // refreshes the recycler view to show items which are still currently in the database
    private fun refreshGames(){
        adapter.list = dataManager.allGames()
        binding.historyrv.adapter = adapter
    }
        // create the ability for items in the recycler view to be moved
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

    // enables items in recycler view to be moved
    private fun handleDragging() {
        val dragCallBack = DragCallBack()
        val touchHelper = ItemTouchHelper(dragCallBack)
        touchHelper.attachToRecyclerView(binding.historyrv)
    }
}