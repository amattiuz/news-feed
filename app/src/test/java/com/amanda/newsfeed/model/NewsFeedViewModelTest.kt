package com.amanda.newsfeed.model

import androidx.paging.*
import com.amanda.newsfeed.api.CbcNewsService
import com.amanda.newsfeed.data.Images
import com.amanda.newsfeed.data.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NewsFeedViewModelTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var model: NewsFeedViewModel

    @Mock
    private lateinit var service: CbcNewsService

    //mocking will be done manually, mockito it not working with flow
    //TODO find out why stubbing did not work
    private lateinit var repo: NewsFeedRepository

    @Before
    fun setup() {
        repo = FakeRepo(service)
        model = NewsFeedViewModel(coroutinesTestRule.testDispatcherProvider, repo)

    }

    @Test
    fun `stream contains all items fetched from server`() {
     coroutinesTestRule.testDispatcher.runBlockingTest {
           model.newsStream().collect {
              assertEquals(itemsList().size, it.collectData().size)
          }
        }
    }

    @Test
    fun `newsTypes returns all available types`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            model.newsTypes().collect {
                assertEquals(itemsList().size, it.collectData().size)
            }
        }
    }

    @Test
    fun `filteredNewsStream returns only news of the requested type`() {
        coroutinesTestRule.testDispatcher.runBlockingTest {
            model.filteredNewsStream("video").collect {
                val list = it.collectData()
                assertEquals(1, list.size)
                assertEquals("video", list[0].type)
            }
        }
    }

    private val dcb = object : DifferCallback {
        override fun onChanged(position: Int, count: Int) {}
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
    }


    //super-mega hack that seems to be the only available option to verify the contents of
    //PaginData
    private suspend fun <T : Any> PagingData<T>.collectData(): List<T> {
        val items = mutableListOf<T>()
        val dif = object : PagingDataDiffer<T>(dcb, coroutinesTestRule.testDispatcher) {
            override suspend fun presentNewList(
                previousList: NullPaddedList<T>,
                newList: NullPaddedList<T>,
                newCombinedLoadStates: CombinedLoadStates,
                lastAccessedIndex: Int
            ): Int? {
                for (idx in 0 until newList.size)
                    items.add(newList.getFromStorage(idx))
                return null
            }
        }
        dif.collectFrom(this)
        return items
    }
}

class FakeRepo(service: CbcNewsService): NewsFeedRepository(service) {

    override fun newsStream(pagingConfig: PagingConfig): Flow<PagingData<NewsItem>> {
        return flowOf(PagingData.from(itemsList()))
    }
}

fun itemsList() = listOf(
    NewsItem(123, Images("http://tiny.cc/mhn6tz"), "Dec 14 2020, 02:38:00 AM UTC", "One thing", "story" ),
    NewsItem(456, Images("http://tiny.cc/mhn6tz"), "Dec 16 2020, 02:38:00 PM UTC", "Some other thing", "video" ),
    NewsItem(789, Images("http://tiny.cc/mhn6tz"), "Dec 10 2020, 12:30:00 AM UTC", "News of the day", "story" ),
    NewsItem(211, Images("http://tiny.cc/mhn6tz"), "Dec 24 2020, 10:30:00 PM UTC", "It is almost christmas", "story" )
)