package com.amanda.newsfeed.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amanda.newsfeed.api.CbcNewsService
import kotlinx.coroutines.Dispatchers

class ViewModelFactory(private val service: CbcNewsService) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsFeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsFeedViewModel(repo = NewsFeedRepository(service)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}