package com.example.firstviewsactivity

import java.io.Serializable

data class Game(
    val name: String,
    val thumbnail: String
) : Serializable