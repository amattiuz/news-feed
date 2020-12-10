package com.amanda.newsfeed.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amanda.newsfeed.R
import com.amanda.newsfeed.api.CbsNewsServiceAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = CbsNewsServiceAdapter.create()

        btn.setOnClickListener {
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

        Log.d("amanca", "end of onCreate")
        txt.text = "DONE"
    }
}