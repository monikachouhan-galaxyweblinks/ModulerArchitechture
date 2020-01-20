package com.networking.client.server

import com.gwl.model.FeedResponse
import com.gwl.model.ResponseData
import com.networking.service.AuthenticationService
import com.pilgrimnetworking.response.ApiResponse
import retrofit2.Call
import retrofit2.Retrofit

class NetworkAPI(retrofit: Retrofit) : NetworkAPIContract() {

    private val authService = retrofit.create(AuthenticationService::class.java)


    override suspend fun getList(): Call<ApiResponse<com.gwl.model.ResponseData>> {
        return authService.getList()

    }

    override suspend fun getFeedList(): Call<ApiResponse<com.gwl.model.FeedResponse?>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}

