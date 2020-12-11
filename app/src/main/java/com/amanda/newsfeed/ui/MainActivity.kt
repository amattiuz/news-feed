package com.amanda.newsfeed.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.amanda.newsfeed.R
import com.amanda.newsfeed.api.CbsNewsServiceAdapter
import com.amanda.newsfeed.model.NewsFeedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("amanda", "onCreate")
        // connection to CBC API
        //val service = CbsNewsServiceAdapter.create()

        // adapter to show the list of news
        Log.d("amanda", "Creating adapter")
        val adapter = NewsFeedPagingAdapter()
        list.adapter = adapter

        // viewmodel that connects the ui to the news source
        Log.d("amanda", "getting viewmodel")
        val model: NewsFeedViewModel by viewModels()

        // observe the news stream, send data to adapter
        // do no block main thread
        Log.d("amanda", "Gooooooooooooo")
        launch {
            model.getNewsStream().collectLatest { adapter.submitData(it) }
        }



        /*btn.setOnClickListener {
            launch(Dispatchers.Main) {
                try {
                    val response = service.getNews(1)
                    Log.d("amanca CBS API", "response $response")
                    txt.text = "CALLED"
                } catch (exception: IOException) {
                    Log.e("amanca ERROR", "I/O EXCEPTION")
                } catch (exception: HttpException) {
                    Log.e("amanca ERROR", "HTTP EXCEPTION")
                }
            }
        }
        txt.text = "DONE"*/
    }
}