package com.example.firstviewsactivity

import android.graphics.Paint
import android.widget.TextView

fun TextView.toggleStrikeThrough(on: Boolean){
    if (on){
        this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }else{
        this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}