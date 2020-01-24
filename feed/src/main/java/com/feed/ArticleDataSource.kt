package com.feed
/**
 * @author GWL
 */
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.gwl.core.datasource.PaginationDataSource
import com.gwl.core.datasource.PagingDataSource
import com.gwl.model.ArticlesItem
import com.networking.result.APIError

class ArticleDataSource(model: PaginationDataSource<ArticlesItem>,
                        override val isPagination: Boolean)
    : PagingDataSource<ArticlesItem>(model) {

    override val errorLiveData: MutableLiveData<APIError>? = ArticleListErrorLiveData
    override val refreshingLiveData: MutableLiveData<Boolean>? = ArticleListRefreshingLiveData
    override val availableItemCountLiveData: MutableLiveData<Int>? = ArticleCountLiveData
}

class ArticleDataSourceFactory(private val model: PaginationDataSource<ArticlesItem>,
                               private val isPagination: Boolean = true)
    : DataSource.Factory<Int, ArticlesItem>() {

    override fun create(): DataSource<Int, ArticlesItem> {
        return ArticleDataSource(model, isPagination)
    }
}

object ArticleListRefreshingLiveData : MutableLiveData<Boolean>()

object ArticleListErrorLiveData : MutableLiveData<APIError>()

object ArticleCountLiveData : MutableLiveData<Int>()