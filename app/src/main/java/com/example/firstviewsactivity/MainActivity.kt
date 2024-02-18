package com.example.firstviewsactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import com.example.firstviewsactivity.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
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
        fetchData("https://www.freetogame.com/api/games?platform=pc")
        // https://demonuts.com/Demonuts/JsonTest/Tennis/json_parsing.php
    }

//    private fun processQuoteJson(jsonString:String) : String{
//        val jsonObject = JSONObject(jsonString)
//
//
//        return ""
//    }


    private fun fetchData(urlString: String){
        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET, urlString,
            { response ->
                val data = response.toString()
                val jsonArray = JSONArray(data)
                val list: MutableList<Games> = ArrayList()
                updateTextView(jsonArray[0].toString())
                for (i in 0..jsonArray.length()-1){
                    val arrObject = jsonArray.getJSONObject(i)
                    val name = arrObject.getString("title")
                    //val thumbnail = arrObject.getString("thumbnail")
                    val g = Games(name)
                    list.add(g)
                }
                val spinner = binding.spinner


            },

            { error ->
                val error = error.toString()
                Log.e("error", error)
            })
        queue.add(request)
//        val thread = Thread{
//            try {
//
//                val url = URL(urlString)
//                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
//                connection.connectTimeout = 10000
//                connection.readTimeout = 10000
//                connection.requestMethod = "GET"
//                connection.connect()
//
//                val responseCode = connection.responseCode
//                if (responseCode == HttpURLConnection.HTTP_OK){
//                    val scanner = Scanner(connection.inputStream).useDelimiter("\\A")
//                    val text = if (scanner.hasNext()) scanner.next() else ""
//                    val quote = processQuoteJson(text)
//                    //updateTextView(text)
//                    updateTextView(quote)
//                } else {
//                    updateTextView("The server returned an error: $responseCode")
//                }
//            } catch (e: IOException){
//                updateTextView("An error occurred updating data from the server")
//            }
//        }
//        thread.start()
    }

    private fun updateTextView(text:String){
        runOnUiThread {
            binding.textView.text = text
        }
    }
}