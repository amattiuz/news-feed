package com.amanda.newsfeed.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.amanda.newsfeed.api.CbcNewsService
import com.amanda.newsfeed.api.CbsNewsServiceAdapter
import com.amanda.newsfeed.data.NewsItem
import kotlinx.coroutines.flow.Flow

class NewsFeedViewModel : ViewModel() {
    private var service: CbcNewsService = CbsNewsServiceAdapter.create()

    fun getNewsStream(): Flow<PagingData<NewsItem>> = Pager(
        config = PagingConfig(PAGING_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { NewsPagingSource(service) }
    ).flow.cachedIn(viewModelScope)
}