package com.example.firstviewsactivity


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firstviewsactivity.databinding.HistoryCardLayoutBinding



class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    var list = mutableListOf<Game>()


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = HistoryCardLayoutBinding.bind(itemView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.history_card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cardView = holder.binding
        val item = list[position]

        cardView.gametitle.text = item.name
        Glide.with(holder.itemView).load(item.thumbnail).into(cardView.imageView2)

    }

    fun addItem(item : Game){
        list.add(item)
        notifyItemInserted(list.lastIndex)
    }
}