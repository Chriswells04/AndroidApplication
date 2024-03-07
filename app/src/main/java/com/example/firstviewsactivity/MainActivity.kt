package com.example.firstviewsactivity


import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
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
    private lateinit var dataManager: DataManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        dataManager = DataManager(this)
        setContentView(view)

        // checks if internet is available
        if (isNetworkAvailable(this)){
            binding.refresh.visibility = View.INVISIBLE
            // opens activity with data select from the spinner
            binding.action.setOnClickListener {
                dataManager.add(game)
                val intent = Intent(this, SpinnerActivity::class.java)
                intent.putExtra("object", game)
                startActivity(intent)
            }

        }else{
            binding.action.visibility = View.INVISIBLE
            binding.refresh.visibility = View.VISIBLE

            binding.refresh.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }


        // opens activity to display recycler view of previous searches
        binding.previous.setOnClickListener {
            val intent = Intent(this, PreviousActivity::class.java)
            startActivity(intent)
        }

        // fetches data from API
        fetchData("https://www.freetogame.com/api/games?platform=pc")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listTitle)
        binding.spinner.adapter = adapter

        updateSpinner()
    }

    // process json string and sorts it into separate lists
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

    // code adapted from Yoshimitsu (2019)
    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }
    // end of adapted code

    // fetches data from the api, ensures get response can time out if response takes too long
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

    // displays errors to user if requests fail or errors occur
    private fun updateTextView(text: String) {
        runOnUiThread {
            binding.textView.text = text
        }
    }

    // updates the spinner populating it with data upon app being launched
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