package com.amanda.newsfeed.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.amanda.newsfeed.BASE_URL
import com.amanda.newsfeed.NewsApplicationInterface
import com.amanda.newsfeed.R
import com.amanda.newsfeed.api.CbsNewsServiceAdapter
import com.amanda.newsfeed.model.NewsFeedViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope(), AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var newsViewModel: NewsFeedViewModel
    private var filter: String = "ALL"
    private val adapter = NewsFeedPagingAdapter()
    private lateinit var typeAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init variables
        //get the view model using a custom factory
        //using a custom factory is a way to allow mocking for unit and instrumented test
        newsViewModel = ViewModelProvider(
            this,
            (application as NewsApplicationInterface).provideViewModelFactory(
                CbsNewsServiceAdapter.create(BASE_URL)
            )
        ).get(NewsFeedViewModel::class.java)

        //set the recycler view adapter with a load state footer to let the user know if
        //internet connection is lost - loading indication is a free bonus from the new
        //paging3 library ;-)
        list.adapter = adapter.withLoadStateFooter(footer = ErrorMessageFooterAdapter { adapter.retry() })

        //adapter to populate the spinner
        typeAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayListOf("ALL")
        )
        news_type_spinner.adapter = typeAdapter
        news_type_spinner.onItemSelectedListener = this

       //fetch all news to begin with
       updateNews()
       //fetch news types for filtering
       updateTypes()
    }

    private fun updateNews() {
        // observe the news stream, send data to adapter
        // do no block main thread
        launch {
            newsViewModel.newsStream()
                    .distinctUntilChanged()
                    .collectLatest {
                        adapter.submitData(it)
                    }
        }
    }

    private fun updateNewsWithFilter() {
        // observe the news stream, send data to adapter
        // do no block main thread
        launch {
            newsViewModel.filteredNewsStream(filter)
                    .distinctUntilChanged()
                    .collectLatest {
                        adapter.submitData(it)
                    }
        }
    }

    private fun updateTypes() {
        //updates spinner content according to values from server
        //uses values from the adapter to avoid mismatching issues
        launch {
            //Your adapter's loadStateFlow here
            adapter.loadStateFlow.
            distinctUntilChangedBy {
                it.refresh
            }.collect {
                val list = adapter.snapshot().items.map { it.type }.distinct()
                if(filter == "ALL") {
                    typeAdapter.clear()
                    typeAdapter.add("ALL")
                    typeAdapter.addAll(list)
                }
            }
        }
    }

    // Spinner interactions
    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
       filter = news_type_spinner.selectedItem as String
       if(filter == "ALL") {
           updateNews()
           updateTypes()
       }
       else {
           updateNewsWithFilter()
       }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        filter = "ALL"
    }
}