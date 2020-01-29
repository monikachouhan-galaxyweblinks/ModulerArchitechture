package com.gwl.playerfeed
/**
 * @author GWL
 */
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.gwl.core.datasource.PaginationDataSource
import com.gwl.core.datasource.PagingDataSource
import com.gwl.model.MediaFeed
import com.networking.result.APIError

class MediaDataSource(model: PaginationDataSource<MediaFeed>, override val isPagination: Boolean)
    : PagingDataSource<MediaFeed>(model) {

    override val errorLiveData: MutableLiveData<APIError>? = MediaListErrorLiveData
    override val refreshingLiveData: MutableLiveData<Boolean>? = MediaListRefreshingLiveData
    override val availableItemCountLiveData: MutableLiveData<Int>? = MediaCountLiveData
}

class MediaDataSourceFactory(private val model: PaginationDataSource<MediaFeed>,
                             private val isPagination: Boolean = true)
    : DataSource.Factory<Int, MediaFeed>() {

    override fun create(): DataSource<Int, MediaFeed> {
        return MediaDataSource(model, isPagination)
    }
}

object MediaListRefreshingLiveData : MutableLiveData<Boolean>()

object MediaListErrorLiveData : MutableLiveData<APIError>()

object MediaCountLiveData : MutableLiveData<Int>()