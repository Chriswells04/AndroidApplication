package com.example.firstviewsactivity

import android.content.Context

import android.database.sqlite.SQLiteDatabase
import android.widget.Toast


class DataManager(private val context: Context) {
    private val db : SQLiteDatabase = context.openOrCreateDatabase("Games", Context.MODE_PRIVATE, null)
    init {
        val gamesCreateQuery = "CREATE TABLE IF NOT EXISTS `Games` ( `Name` TEXT NOT NULL, `Thumbnail` TEXT NOT NULL, `GameURL` TEXT NOT NULL,  PRIMARY KEY(`Name`))"
        db.execSQL(gamesCreateQuery)
    }

    // checks if game exists already in database
    private fun checkGameExists(game:Game):Boolean{
            val checkQuery = "SELECT * FROM Games WHERE name = '${game.name}'"
            val cursor = db.rawQuery(checkQuery, null)

            val boolVal = cursor.moveToFirst()
            cursor.close()
            return boolVal
    }

    // adds game to database if game does not already exist
    fun add(game: Game){
        if (!game.name.contains("'")){
            if (!checkGameExists(game)){
                val query = "INSERT INTO Games (name, thumbnail, gameurl) VALUES ('${game.name}','${game.thumbnail}','${game.gameUrl}')"
                db.execSQL(query)
            }
        }
        else{
            Toast.makeText(context, "Game cannot be saved to previous search", Toast.LENGTH_SHORT).show()
        }

    }

    // deletes game from database
    fun deleteGame(game:Game){
        val query = "DELETE FROM Games WHERE name = '${game.name}'"
        db.execSQL(query)
    }

    // returns all games currently stored in the database to a MutableList
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