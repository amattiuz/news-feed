package com.amanda.newsfeed.model

import com.amanda.newsfeed.api.CbcNewsService
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
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



    @Before
    fun setup() {
        model = NewsFeedViewModel()
    }

    @Test
    fun `newsStream returns valid flow of paging data`() {
        val page = model.newsStream().first()
    }
}