package com.networking.client.server

import com.gwl.model.ArticlesItem
import com.gwl.model.InstaFeed
import com.gwl.model.ResponseData
import com.networking.response.ApiResponse
import com.networking.result.APIResult
import retrofit2.Call


abstract class NetworkAPIContract {

    abstract suspend fun getList(): Call<ApiResponse<ResponseData>>
    abstract suspend fun getInstaFeeds(): APIResult<List<InstaFeed>>
    abstract suspend fun getPlayerFeeds(
        page: Int = 1,
        count: Int = 10
    ): APIResult<List<ArticlesItem>>

    abstract suspend fun getFeedList(
        page: Long = 1,
        count: Int = 50
    ): APIResult<List<ArticlesItem>>


}