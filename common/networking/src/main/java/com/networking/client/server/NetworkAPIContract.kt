package com.networking.client.server

import com.gwl.model.FeedResponse
import com.gwl.model.ResponseData
import com.pilgrimnetworking.response.ApiResponse
import retrofit2.Call


abstract class NetworkAPIContract {

    abstract suspend fun getList(): Call<ApiResponse<ResponseData>>
    abstract suspend fun getFeedList(): Call<FeedResponse?>


}