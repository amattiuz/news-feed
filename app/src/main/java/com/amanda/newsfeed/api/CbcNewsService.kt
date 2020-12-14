package com.amanda.newsfeed.api

import com.amanda.newsfeed.data.NewsItem
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val START_PAGE_INDEX = 1

interface CbcNewsService {
    @GET("items?lineupSlug=news")
    suspend fun getNews(@Query("page") page: Int): List<NewsItem>
}

object CbsNewsServiceAdapter{

    fun create(baseUrl: String): CbcNewsService {
        val client = OkHttpClient.Builder()
            .addInterceptor(StethoInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CbcNewsService::class.java)
    }
}