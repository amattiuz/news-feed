package com.amanda.newsfeed.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsFeedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsFeedViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}