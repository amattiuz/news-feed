package com.amanda.newsfeed.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.amanda.newsfeed.OpenForTesting
import com.amanda.newsfeed.api.CbcNewsService
import com.amanda.newsfeed.data.NewsItem
import kotlinx.coroutines.flow.Flow

@OpenForTesting
class NewsFeedRepository (private val service: CbcNewsService) {

   fun newsStream(pagingConfig: PagingConfig = defaultPagingConfig()): Flow<PagingData<NewsItem>>{
       return Pager(
           config = pagingConfig,
           pagingSourceFactory = { NewsPagingSource(service) }
       ).flow
   }

    private fun defaultPagingConfig(): PagingConfig = PagingConfig(
        pageSize = PAGING_SIZE,
        enablePlaceholders = false
    )

}