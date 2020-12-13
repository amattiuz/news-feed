package com.amanda.newsfeed.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.amanda.newsfeed.R
import kotlinx.android.synthetic.main.loading_network_error_message.view.*

class ErrorMessageFooterAdapter (
        private val retryFunction: () -> Unit
) : LoadStateAdapter<ErrorMessageViewHolder>() {
    override fun onBindViewHolder(holder: ErrorMessageViewHolder, loadState: LoadState) {

        val progressBar = holder.itemView.progress_bar
        val btnRetry = holder.itemView.retry_button
        val txtErrorMsg = holder.itemView.error_msg

        //UI elements visibility according to current loading state
        val errorVisibility = loadState !is LoadState.Loading
        btnRetry.isVisible = errorVisibility
        txtErrorMsg.isVisible = errorVisibility
        progressBar.isVisible = !errorVisibility

        //Assuming load state errors are caused by internet connection problems
        if(loadState is LoadState.Error) {
            txtErrorMsg.text = holder.itemView.resources.getText(R.string.no_internet)
        }

        //Call the given retry funcion when user clicks the button
        btnRetry.setOnClickListener { retryFunction.invoke() }
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
    ): ErrorMessageViewHolder = ErrorMessageViewHolder(
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.loading_network_error_message, parent,false)
    )
}

class ErrorMessageViewHolder(private val view: View) :  RecyclerView.ViewHolder(view)