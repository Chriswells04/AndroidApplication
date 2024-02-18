package com.example.firstviewsactivity

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.firstviewsactivity.databinding.ActivitySpinnerLayoutBinding


class Spinner(context: Context, list: MutableList<Games>) : ArrayAdapter<Games>(context, 0, list) {
    private lateinit var binding: ActivitySpinnerLayoutBinding


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return super.getDropDownView(position, convertView, parent)
    }

    private fun myView(position: Int, convertView: View?, parent: ViewGroup): View{
        val list = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.activity_spinner_layout, parent, false)

        list?.let {
            val text = binding.textView2

            text.text = list.name

        }
        return view
    }

}