package com.amanda.newsfeed.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amanda.newsfeed.R
import com.amanda.newsfeed.model.NewsFeedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    lateinit var newsViewModel: NewsFeedViewModel
    lateinit var adapter: NewsFeedPagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init variables
        newsViewModel = defaultViewModelProviderFactory.create(NewsFeedViewModel::class.java)
        adapter = NewsFeedPagingAdapter()

        //set the recycler view adapter
        list.adapter = adapter

        // observe the news stream, send data to adapter
        // do no block main thread
        launch {
            newsViewModel.getNewsStream().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}