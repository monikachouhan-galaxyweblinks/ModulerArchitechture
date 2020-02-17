package com.gwl.core.datasource

import android.os.Parcelable
import com.gwl.networking.client.server.NetworkAPIContract
import com.gwl.networking.result.APIResult

interface PaginationDataSource<T> : Parcelable {
    suspend fun fetch(page: Int, count: Int): APIResult<List<T>>
}

abstract class PathDataSource<T>(val api: NetworkAPIContract) : PaginationDataSource<T>
