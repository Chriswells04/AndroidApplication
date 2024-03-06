package com.example.firstviewsactivity

import java.io.Serializable

// data class to store game objects from API
data class Game(
    val name: String,
    val thumbnail: String,
    val gameUrl: String
) : Serializable