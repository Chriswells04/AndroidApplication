package com.example.firstviewsactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firstviewsactivity.databinding.HistoryCardLayoutBinding

// creates an adapter to display data to the RecyclerView
class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    var list = mutableListOf<Game>()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = HistoryCardLayoutBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_card_layout, parent, false)
        return ViewHolder(view)
    }

    // returns the size of the list
    override fun getItemCount(): Int {
        return list.count()
    }

    // connects to the card layout to display each object with its data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.binding
        val item = list[position]

        cardView.gametitle.text = item.name
        Glide.with(holder.itemView).load(item.thumbnail).into(cardView.imageView2)
    }

    // updates the list for the recycler view
    fun updateListOfGames (games: MutableList<Game>){
        list = games
    }
}