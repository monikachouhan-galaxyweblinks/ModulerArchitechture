package com.gwl.feeds
/**
 * @author GWL
 */
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.gwl.core.datasource.PaginationDataSource
import com.gwl.core.datasource.PagingDataSource
import com.gwl.model.InstaFeed
import com.networking.result.APIError

class MediaDataSource(model: PaginationDataSource<InstaFeed>, override val isPagination: Boolean)
    : PagingDataSource<InstaFeed>(model) {

    override val errorLiveData: MutableLiveData<APIError>? = MediaListErrorLiveData
    override val refreshingLiveData: MutableLiveData<Boolean>? = MediaListRefreshingLiveData
    override val availableItemCountLiveData: MutableLiveData<Int>? = MediaCountLiveData
}

class MediaDataSourceFactory(private val model: PaginationDataSource<InstaFeed>,
                             private val isPagination: Boolean = true)
    : DataSource.Factory<Int, InstaFeed>() {

    override fun create(): DataSource<Int, InstaFeed> {
        return MediaDataSource(model, isPagination)
    }
}

object MediaListRefreshingLiveData : MutableLiveData<Boolean>()

object MediaListErrorLiveData : MutableLiveData<APIError>()

object MediaCountLiveData : MutableLiveData<Int>()