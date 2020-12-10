package com.amanda.newsfeed.api

import com.amanda.newsfeed.data.NewsItem
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.cbc.ca/aggregate_api/v1/"

interface CbcNewsService {
    @GET("items?lineupSlug=news")
    suspend fun getNews(@Query("page") page: Int): List<NewsItem>
}

object CbsNewsServiceAdapter{

    fun create(): CbcNewsService {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor(StethoInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CbcNewsService::class.java)
    }
}