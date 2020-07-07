package com.aucto.core.datasource

import androidx.annotation.NonNull
import androidx.paging.PagedList
import com.aucto.core.R
import com.aucto.networking.dispatchers.DispatcherProvider
import com.aucto.networking.dispatchers.DispatcherProviderImpl
import com.aucto.networking.result.APIError
import com.aucto.networking.result.APIErrorType
import com.aucto.networking.result.APIResult
import com.aucto.networking.util.StringUtil.getString
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaginationBoundryCallBack<T>(
    val source: PaginationDataSource<T>,
    val pageConfiguration: PageConfiguration,
    val boundryCallbackListener: BoundryCallbackListener<T>,
    val dispatcher: DispatcherProvider = DispatcherProviderImpl()
) : PagedList.BoundaryCallback<T>() {


    //region - Private properties
    // Keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1
    // Avoid triggering multiple requests in the same time
    private var isRequestInProgress = false
    // LiveData of network errors.
    private var hasNextItem: Boolean = true
    //endregion

    //region - Public functions
    override fun onZeroItemsLoaded() {
        boundryCallbackListener.onZeroItemsLoaded()
        requestAndSaveData(lastRequestedPage)
    }

    override fun onItemAtEndLoaded(@NonNull itemAtEnd: T) {
        requestAndSaveData(lastRequestedPage)
    }

    override fun onItemAtFrontLoaded(itemAtFront: T) {
        boundryCallbackListener.onItemAtFrontLoaded()
    }

    fun onSuccess(list: List<T>, pageSize: Int) {
        boundryCallbackListener.insertItem(list, pageSize)
        lastRequestedPage++
        isRequestInProgress = false
    }
    //endregion

    fun onRefresh() {
        hasNextItem = true
        lastRequestedPage = 1
        isRequestInProgress = false
        requestAndSaveData(lastRequestedPage)
    }

    //region - Private functions
    private fun requestAndSaveData(pageSize: Int) {
        //Exiting if the request is in progress
        if (isRequestInProgress) return
        if (!hasNextItem) return
        //Set to true as we are starting the network request
        isRequestInProgress = true
        GlobalScope.launch(dispatcher.io) {
            val result = source.fetch(lastRequestedPage, pageConfiguration.count)
            withContext(dispatcher.main) {
                when (result) {
                    is APIResult.Success -> {
                        val data = result.response.data
                        data?.also {
                            onSuccess(it, pageSize)
                        }
                        hasNextItem = result.response.hasNextPage()
                    }
                    is APIResult.Failure -> {
                        isRequestInProgress = false
                        parseException(result.details)
                        hasNextItem = false
                    }
                }
            }
        }
    }

    private fun parseException(exception: APIError) {
        when (exception.type) {
            is APIErrorType.CallNotTried -> {
            }
            is APIErrorType.Network -> boundryCallbackListener.OnError(getString(R.string.error_timeout))
            is APIErrorType.General -> {
                if (!exception.message.isNullOrEmpty()) {
                    boundryCallbackListener.OnError(exception.message ?: "")
                } else {
                    boundryCallbackListener.OnError(getString(R.string.unknown_error))
                }
            }
            else -> boundryCallbackListener.OnError(getString(R.string.unknown_error))
        }
    }
    //endregion
}

interface BoundryCallbackListener<T> {
    fun onZeroItemsLoaded()
    fun insertItem(item: List<T>, pageSize: Int)
    fun onItemAtFrontLoaded()
    fun OnError(message: String)
}