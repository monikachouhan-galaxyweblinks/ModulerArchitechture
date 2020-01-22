package com.feed

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.feed.datasource.FeedDataSource
import com.gwl.core.BaseViewModel
import com.gwl.model.ArticlesItem
import com.gwl.model.FeedResponse
import com.networking.client.server.NetworkAPI

class FeedViewModel(val pilgrimAPI: NetworkAPI) : BaseViewModel() {

    companion object {
        const val FETCH_SIZE = 5
        const val PREFETCH_DISTANCE = 20
    }

    val feedResponse: ObservableField<FeedResponse> by lazy { ObservableField<FeedResponse>() }
    var adapter: FeedsAdapter = FeedsAdapter()
    val pagedListConfig = PagedList.Config.Builder()
        .setInitialLoadSizeHint(FETCH_SIZE)
        .setPageSize(FETCH_SIZE)
        .setPrefetchDistance(PREFETCH_DISTANCE)
        .setEnablePlaceholders(false)
        .build()

    fun initPager(): LiveData<PagedList<ArticlesItem>> {
        return LivePagedListBuilder<Int, ArticlesItem>(
            ArticleDataSourceFactory(
                FeedDataSource(),
                true
            ), pagedListConfig
        ).build()
    }

    /* fun getList() {
         GlobalScope.launch(Dispatchers.IO) {
             val response = pilgrimAPI.getFeeds(1L,20)
             Log.d("FeedViewModel", "FeedViewModel ${response.execute()}")
         }

     }*/
}