package com.example.firstviewsactivity

import android.content.Context

import android.database.sqlite.SQLiteDatabase


class DataManager(context: Context) {
    private val db : SQLiteDatabase = context.openOrCreateDatabase("Games", Context.MODE_PRIVATE, null)
    init {
        val gamesCreateQuery = "CREATE TABLE IF NOT EXISTS `Games` ( `Name` TEXT NOT NULL, `Thumbnail` TEXT NOT NULL, `GameURL` TEXT NOT NULL,  PRIMARY KEY(`Name`))"
        db.execSQL(gamesCreateQuery)
    }

    fun add(game: Game){
        val query = "INSERT INTO Games (name, thumbnail, gameurl) VALUES ('${game.name}','${game.thumbnail}','${game.gameUrl}')"
        db.execSQL(query)
    }

    fun allGames() : MutableList<Game>{
        val games = mutableListOf<Game>()
        val cursor = db.rawQuery("SELECT * FROM Games", null)
        if (cursor.moveToFirst()){
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("Name"))
                val thumbnail = cursor.getString(cursor.getColumnIndexOrThrow("Thumbnail"))
                val gameUrl = cursor.getString(cursor.getColumnIndexOrThrow("GameURL"))
                val game = Game(name, thumbnail, gameUrl)
                games.add(game)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return games
    }
}