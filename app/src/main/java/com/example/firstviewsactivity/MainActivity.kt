package com.example.firstviewsactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firstviewsactivity.databinding.ActivityMainBinding
import org.json.JSONArray
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.button.setOnClickListener{
            getCar()
        }
    }

    private fun getCar(){
        fetchData("https://ergast.com/api/f1/drivers.json?callback=myParser")
    }

    private fun processQuoteJson(jsonString:String) : String{
        val jsonArray = JSONArray(jsonString)
        return jsonArray[0].toString()
    }

    private fun fetchData(urlString: String){
        val thread = Thread{
            try {
                val url = URL(urlString)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK){
                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
                    val text = if (scanner.hasNext()) scanner.next() else ""

                    val quote = processQuoteJson(text)
                    updateTextView(quote)
                } else {
                    updateTextView("The server returned an error: $responseCode")
                }
            } catch (e: IOException){
                updateTextView("An error occurred updating data from the server")
            }
        }
        thread.start()
    }

    private fun updateTextView(text:String){
        runOnUiThread {
            binding.textView.text = text
        }
    }
}