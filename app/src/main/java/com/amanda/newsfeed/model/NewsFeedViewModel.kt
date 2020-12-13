package com.amanda.newsfeed.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.amanda.newsfeed.api.CbcNewsService
import com.amanda.newsfeed.api.CbsNewsServiceAdapter
import com.amanda.newsfeed.data.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsFeedViewModel : ViewModel() {
    private var service: CbcNewsService = CbsNewsServiceAdapter.create()

    fun newsStream(): Flow<PagingData<NewsItem>> = Pager(
            config = PagingConfig(PAGING_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(service) }
        ).flow.cachedIn(viewModelScope)

    fun filteredNewsStream(typeFilter: String): Flow<PagingData<NewsItem>> = Pager(
            config = PagingConfig(PAGING_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(service) }
    ).flow
            .map { data -> data.filter { it.type == typeFilter } }
            .cachedIn(viewModelScope)

    fun newsTypes(): Flow<PagingData<String>> = Pager(
            config = PagingConfig(PAGING_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(service) }
    ).flow
            .map { data -> data.mapSync { it.type } }
            .cachedIn(viewModelScope)
}