package com.amanda.newsfeed.model

import androidx.paging.PagingSource
import com.amanda.newsfeed.OpenForTesting
import com.amanda.newsfeed.api.CbcNewsService
import com.amanda.newsfeed.api.START_PAGE_INDEX
import com.amanda.newsfeed.data.NewsItem
import retrofit2.HttpException
import java.io.IOException

const val PAGING_SIZE = 10

@OpenForTesting
class NewsPagingSource (private val service: CbcNewsService) : PagingSource<Int, NewsItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val position = params.key ?: START_PAGE_INDEX
        return try {
            val response = service.getNews(position)
            LoadResult.Page(
                data = response,
                prevKey = if(position == START_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}