package com.aucto.core.datasource

import android.os.Parcelable
import com.aucto.networking.client.server.NetworkAPIContract
import com.aucto.networking.result.APIResult

interface PaginationDataSource<T> : Parcelable {
    suspend fun fetch(page: Int, count: Int): APIResult<List<T>>
}

abstract class PathDataSource<T>(val api: NetworkAPIContract) : PaginationDataSource<T>
