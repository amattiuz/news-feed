package com.amanda.newsfeed.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.amanda.newsfeed.data.NewsItem

class NewsFeedPagingAdapter : PagingDataAdapter<NewsItem, NewsItemViewHolder>(newsComparator) {

    companion object {
        private val newsComparator = object : DiffUtil.ItemCallback<NewsItem>() {
            override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int)
            = holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NewsItemViewHolder.create(parent)
}