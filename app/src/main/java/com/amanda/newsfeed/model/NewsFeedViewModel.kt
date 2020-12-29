package com.amanda.newsfeed.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.amanda.newsfeed.DefaultDispatcherProvider
import com.amanda.newsfeed.DispatcherProvider
import com.amanda.newsfeed.OpenForTesting
import com.amanda.newsfeed.data.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@OpenForTesting
class   NewsFeedViewModel(
    private val dispatchers: DispatcherProvider = DefaultDispatcherProvider(),
    private val repo: NewsFeedRepository
) : ViewModel() {

    fun filteredNewsStream(typeFilter: String): Flow<PagingData<NewsItem>> = repo.newsStream()
            .map { data -> data.filter { it.type == typeFilter } }
            .cachedIn(viewModelScope)

    suspend fun newsTypes(): Flow<PagingData<String>> {
        return withContext(dispatchers.default()) {
            repo.newsStream()
                .map { data -> data.map { it.type } }
                .cachedIn(viewModelScope)
        }
    }

    suspend fun newsStream(): Flow<PagingData<NewsItem>>  {
        return withContext(dispatchers.default()) {
            repo.newsStream().cachedIn(viewModelScope)
        }
    }

    private fun defaultPagingConfig(): PagingConfig = PagingConfig(
        pageSize = PAGING_SIZE,
        enablePlaceholders = false
    )

}