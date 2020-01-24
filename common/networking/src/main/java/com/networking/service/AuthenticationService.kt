package com.networking.service

import com.gwl.model.FeedResponse
import com.gwl.model.ResponseData
import com.networking.response.ApiResponse
import retrofit2.Call
import retrofit2.http.GET


interface AuthenticationService {

    @GET("glide.json")
    fun getList(): Call<ApiResponse<ResponseData>>

    @GET("/v2/everything")
    fun fetchFeedList(
        @retrofit2.http.Query("q") q: String?,
        @retrofit2.http.Query("apiKey") apiKey: String?,
        @retrofit2.http.Query("page") page: Long,
        @retrofit2.http.Query("pageSize") pageSize: Int
    ): Call<FeedResponse>
}