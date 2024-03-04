package com.example.firstviewsactivity



import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstviewsactivity.databinding.ActivityPreviousBinding
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

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
        loadList()

    }


    private fun saveList(){
        val fileOutputStream = openFileOutput("previous.dat", Context.MODE_PRIVATE)
        val objectOutputStream = ObjectOutputStream(fileOutputStream)
        objectOutputStream.writeObject(adapter.list)
        objectOutputStream.close()
        fileOutputStream.close()
    }

    private fun loadList(){
        try{
            val fileInputStream = openFileInput("previous.dat")
            val objectInputStream = ObjectInputStream(fileInputStream)


            val list = objectInputStream.readObject() as? MutableList<Game>

            if (list != null){
                adapter.list = list
            }
            objectInputStream.close()
            fileInputStream.close()
        }
        catch (e:java.io.FileNotFoundException){
            // loading has failed
            Toast.makeText(this, "No existing list found", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        saveList()
    }

    override fun onPause() {
        super.onPause()
        saveList()
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