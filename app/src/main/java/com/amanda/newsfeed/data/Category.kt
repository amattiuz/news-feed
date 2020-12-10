package com.amanda.newsfeed.data

data class Category(
    val bannerImage: String,
    val id: Int,
    val image: String,
    val metadata: Metadata,
    val name: String,
    val priority: Int,
    val priorityWhenInlined: Int,
    val slug: String,
    val type: String
)