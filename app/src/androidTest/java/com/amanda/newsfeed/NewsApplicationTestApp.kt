package com.amanda.newsfeed

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.amanda.newsfeed.api.CbcNewsService
import com.amanda.newsfeed.api.CbsNewsServiceAdapter
import com.amanda.newsfeed.data.Images
import com.amanda.newsfeed.data.NewsItem
import com.amanda.newsfeed.model.NewsFeedRepository
import com.amanda.newsfeed.model.NewsFeedViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class NewsApplicationTestApp : NewsApplicationInterface, Application() {

    override fun provideViewModelFactory(service: CbcNewsService): ViewModelProvider.Factory {
        return ViewModelTestFactory()
    }
}

class ViewModelTestFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsFeedViewTestModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsFeedViewTestModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@OpenForTesting
class NewsFeedViewTestModel : NewsFeedViewModel(repo = NewsFeedRepository(CbsNewsServiceAdapter.create(BASE_URL))) {

    val data = PagingData.from(itemsList())

    override suspend fun newsStream(): Flow<PagingData<NewsItem>> = flowOf(data).cachedIn(viewModelScope)


    override fun filteredNewsStream(typeFilter: String): Flow<PagingData<NewsItem>> {
        return flowOf(data).map { data -> data.filter { it.type == typeFilter } }
            .cachedIn(viewModelScope)
    }

    override suspend fun newsTypes(): Flow<PagingData<String>> {
        return flowOf(data) .map { data -> data.mapSync { it.type } }
            .cachedIn(viewModelScope)
    }

    private final fun itemsList() = listOf(
            NewsItem(123, Images("http://tiny.cc/mhn6tz"), "Dec 14 2020, 02:38:00 AM UTC", "One thing", "story" ),
            NewsItem(456, Images("http://tiny.cc/mhn6tz"), "Dec 16 2020, 02:38:00 PM UTC", "Some other thing", "video" ),
            NewsItem(789, Images("http://tiny.cc/mhn6tz"), "Dec 10 2020, 12:30:00 AM UTC", "News of the day", "story" ),
            NewsItem(555, Images("http://tiny.cc/mhn6tz"), "Dec 14 2020, 02:38:00 AM UTC", "Very long title of the news of the very long day", "story" ),
            NewsItem(888, Images("http://tiny.cc/mhn6tz"), "Dec 16 2020, 02:38:00 PM UTC", "NO news today", "video" ),
            NewsItem(111, Images("http://tiny.cc/mhn6tz"), "Dec 10 2020, 12:30:00 AM UTC", "Raptors win!", "sports" ),
            NewsItem(214, Images("http://tiny.cc/mhn6tz"), "Dec 14 2020, 02:38:00 AM UTC", "Watch this video!", "video" ),
            NewsItem(999, Images("http://tiny.cc/mhn6tz"), "Dec 16 2020, 02:38:00 PM UTC", "Some other nice video", "video" ),
            NewsItem(845, Images("http://tiny.cc/mhn6tz"), "Dec 10 2020, 12:30:00 AM UTC", "Stub", "stub" ),
            NewsItem(366, Images("http://tiny.cc/mhn6tz"), "Dec 16 2020, 02:38:00 PM UTC", "Tomorrow will be cold", "stub" ),
            NewsItem(211, Images("http://tiny.cc/mhn6tz"), "Dec 24 2020, 10:30:00 PM UTC", "It is almost christmas", "story" )
        )

}