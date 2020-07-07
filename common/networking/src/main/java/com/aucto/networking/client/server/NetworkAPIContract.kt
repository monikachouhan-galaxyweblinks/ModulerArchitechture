package com.aucto.networking.client.server

import com.aucto.model.*
import com.aucto.networking.response.ApiResponse
import com.aucto.networking.result.APIResult
import retrofit2.Call


abstract class NetworkAPIContract {

    abstract suspend fun postBlog(request: PostBlogRequest): Call<BlogPostResponse>
    abstract suspend fun postNewBlog(request: PostBlogRequest): APIResult<BlogPostResponse>
    abstract suspend fun getList(): Call<ApiResponse<ResponseData>>
    abstract suspend fun getInstaFeeds(token :String): APIResult<List<InstaFeed>>
    abstract suspend fun getPlayerFeeds(
        page: Int = 1,
        count: Int = 10
    ): APIResult<List<ArticlesItem>>

    abstract suspend fun getFeedList(
        page: Long = 1,
        count: Int = 50
    ): APIResult<List<ArticlesItem>>


}