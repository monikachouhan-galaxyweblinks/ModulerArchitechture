package com.networking.client.server

import com.gwl.model.FeedResponse
import com.gwl.model.ResponseData
import com.networking.BuildConfig
import com.networking.service.AuthenticationService
import com.pilgrimnetworking.response.ApiResponse
import retrofit2.Call
import retrofit2.Retrofit

class NetworkAPI(retrofit: Retrofit) : NetworkAPIContract() {

    private val authService = retrofit.create(AuthenticationService::class.java)


    override suspend fun getList(): Call<ApiResponse<ResponseData>> {
        return authService.getList()

    }

    override suspend fun getFeedList(): Call<FeedResponse?> {
       return authService.fetchFeedList("bitcoin","5b0c09c541454cad8d8626d779802b2e",1,10)
    }


}

