package com.amanda.newsfeed.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.amanda.newsfeed.R
import com.amanda.newsfeed.data.NewsItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class NewsItemViewHolder(private val view: View) :  RecyclerView.ViewHolder(view) {

    private val title: TextView = view.findViewById(R.id.news_title)
    private val date: TextView = view.findViewById(R.id.news_date)
    private val image: ImageView = view.findViewById(R.id.news_image)

    private var news: NewsItem? = null

    companion object {
        fun create(parent: ViewGroup): NewsItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item_layout, parent, false)
            return NewsItemViewHolder(view)
        }
    }

    fun bind(news: NewsItem?) = when(news) {
        null -> showPlaceholderData()
        else -> showNewsData(news)
    }

    private fun showPlaceholderData() {
        title.text = itemView.resources.getString(R.string.please_wait)
        date.text = itemView.resources.getString(R.string.loading)
    }

    private fun showNewsData(data: NewsItem) {
        news = data
        title.text = data.title
        date.text = data.readableUpdatedAt
        setImageSource(data.images.square_140)
    }

    private fun setImageSource(url: String) {
        Glide.with(view)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.news_placeholder)
            .apply(RequestOptions().override(140, 140))
            .into(image)
    }

}