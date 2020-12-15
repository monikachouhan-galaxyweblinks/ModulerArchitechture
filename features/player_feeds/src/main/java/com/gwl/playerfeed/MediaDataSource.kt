package com.gwl.playerfeed
/**
 * @author GWL
 */
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.gwl.core.datasource.PaginationDataSource
import com.gwl.core.datasource.PagingDataSource
import com.gwl.model.ArticlesItem
import com.gwl.networking.result.APIError

class MediaDataSource(model: PaginationDataSource<ArticlesItem>, override val isPagination: Boolean)
    : PagingDataSource<ArticlesItem>(model) {

    override val errorLiveData: MutableLiveData<APIError>? = MediaListErrorLiveData
    override val refreshingLiveData: MutableLiveData<List<ArticlesItem>> = MediaListRefreshingLiveData
    override val availableItemCountLiveData: MutableLiveData<Int>? = MediaCountLiveData
}

class MediaDataSourceFactory(private val model: PaginationDataSource<ArticlesItem>,
                             private val isPagination: Boolean = true)
    : DataSource.Factory<Int, ArticlesItem>() {

    override fun create(): DataSource<Int, ArticlesItem> {
        return MediaDataSource(model, isPagination)
    }
}

object MediaListRefreshingLiveData : MutableLiveData<List<ArticlesItem>>()

object MediaListErrorLiveData : MutableLiveData<APIError>()

object MediaCountLiveData : MutableLiveData<Int>()