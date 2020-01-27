package com.gwl.core.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.networking.result.APIError
import com.networking.result.APIResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


abstract class PagingDataSource<T>(open val source: PaginationDataSource<T>, open val isPagination: Boolean = true) : PageKeyedDataSource<Int, T>() {
    open val availableItemCountLiveData: MutableLiveData<Int>? = null
    open val refreshingLiveData: MutableLiveData<Boolean>? = null
    open val loadingLiveData: MutableLiveData<Boolean>? = null
    open val errorLiveData: MutableLiveData<APIError>? = null

    /**
     * Load the initial page of data
     */
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        val loadSize = params.requestedLoadSize

        refreshingLiveData?.postValue(true)
        loadingLiveData?.postValue(true)

        GlobalScope.launch(Dispatchers.Default) {
            val response = source.fetch(1, loadSize)

            val list = parseResponse(response)

            refreshingLiveData?.postValue(false)
            loadingLiveData?.postValue(false)

            val previousPage = null
            val nextPage = if (response is APIResult.Success && response.hasNextPage() && isPagination) {
                2
            } else {
                null
            }
            callback.onResult(list, previousPage, nextPage)
        }
    }

    /**
     * Load subsequent pages
     */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        GlobalScope.launch(Dispatchers.IO) {
            val page = params.key
            val loadSize = params.requestedLoadSize

            loadingLiveData?.postValue(true)
            val response = source.fetch(page, loadSize)
            val list = parseResponse(response)
            loadingLiveData?.postValue(false)

            val nextPage = if (response.hasNextPage()) {
                page + 1
            } else {
                null
            }
            callback.onResult(list, nextPage)
        }
    }

    /**
     * Load previous pages
     */
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        GlobalScope.launch(Dispatchers.IO) {
            val page = params.key
            val loadSize = params.requestedLoadSize

            loadingLiveData?.postValue(true)
            val response = source.fetch(page, loadSize)
            val list = parseResponse(response)
            loadingLiveData?.postValue(false)

            val previousPage = if (page <= 1) {
                null
            } else {
                page - 1
            }
            callback.onResult(list, previousPage)
        }
    }

    private fun parseResponse(result: APIResult<List<T>>): List<T> {
        return when (result) {
            is APIResult.Success -> {
                availableItemCountLiveData?.postValue(result.response.itemCount)
                result.response.data
            }
            is APIResult.Failure -> {
                errorLiveData?.postValue(result.details)
                emptyList()
            }
        }
    }
}