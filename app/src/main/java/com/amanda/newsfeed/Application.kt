package com.amanda.newsfeed

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.amanda.newsfeed.api.CbcNewsService
import com.amanda.newsfeed.model.ViewModelFactory
import com.facebook.stetho.Stetho

const val BASE_URL = "https://www.cbc.ca/aggregate_api/v1/"
@OpenForTesting
class NewsApplication : Application(), NewsApplicationInterface {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }

    override fun provideViewModelFactory(service: CbcNewsService): ViewModelProvider.Factory {
        return ViewModelFactory(service)
    }
}

interface NewsApplicationInterface {
    fun provideViewModelFactory(service: CbcNewsService) : ViewModelProvider.Factory
}

@OpenForTesting
annotation class OpenForTesting