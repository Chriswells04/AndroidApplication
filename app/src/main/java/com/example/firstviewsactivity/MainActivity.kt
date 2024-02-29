package com.example.firstviewsactivity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.firstviewsactivity.databinding.ActivityMainBinding
import org.json.JSONArray
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val listTitle = mutableListOf<String>()
    private val listThumbnail = mutableListOf<String>()
    private val listUrl = mutableListOf<String>()
    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        fetchData("https://www.freetogame.com/api/games?platform=pc")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listTitle)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                Toast.makeText(
                    this@MainActivity,
                    "Game Selected: " + listTitle[position],
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        binding.action.setOnClickListener {
            val intent = Intent(this, SpinnerActivity::class.java)
            intent.putExtra("object", game)
            startActivity(intent)
        }

        updateSpinner()
    }

    private fun processQuoteJson(jsonString: String): MutableList<String> {
        val jsonArray = JSONArray(jsonString)
        for (i in 0..<jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val title = jsonObject.getString("title")
            val thumbnail = jsonObject.getString("thumbnail")
            val gameUrl = jsonObject.getString("game_url")

            listThumbnail.add(thumbnail)
            listTitle.add(title)
            listUrl.add(gameUrl)
        }
        return listTitle
    }

    private fun fetchData(urlString: String) {
        val thread = Thread {
            try {
                val url = URL(urlString)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val text = if (scanner.hasNext()) scanner.next() else ""

                    val quote = processQuoteJson(text)
                    updateSpinner()
                    updateTextView(quote[0])

                } else {
                    updateTextView("The server returned an error: $responseCode")
                }
            } catch (e: IOException) {
                updateTextView("An error occurred updating data from the server")
            }
        }
        thread.start()
    }

    private fun updateTextView(text: String) {
        runOnUiThread {
            binding.textView.text = text
        }
    }

    private fun updateSpinner() {
        runOnUiThread {
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listTitle)
            binding.spinner.adapter = adapter

            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    game = Game(listTitle[position], listThumbnail[position], listUrl[position])
                    Toast.makeText(
                        this@MainActivity,
                        "Game Selected: " + listTitle[position],
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
    }
}