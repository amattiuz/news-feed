package com.amanda.newsfeed.data

data class NewsItem(
    val id: Int,
    val images: Images,
    val readableUpdatedAt: String,
    val title: String,
    val type: String
)