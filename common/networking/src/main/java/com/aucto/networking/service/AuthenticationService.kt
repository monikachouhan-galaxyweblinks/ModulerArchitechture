package com.aucto.networking.service

import com.aucto.model.*
import com.aucto.networking.response.ApiResponse
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Url


interface AuthenticationService {

    @GET("glide.json")
    fun getList(): Call<ApiResponse<ResponseData>>

    @POST
    fun postBlog(
        @Url url: String = "https://jsonplaceholder.typicode.com/posts",
        @Body signupUser: PostBlogRequest
    ): Call<BlogPostResponse>

    @POST
    fun postNewBlog(
        @Url url: String = "https://jsonplaceholder.typicode.com/posts",
        @Body signupUser: PostBlogRequest
    ): Call<ApiResponse<BlogPostResponse>>

    //079dac74a5f94ebdb990ecf61c8854b7
    @GET("v2/everything?q=movies&apiKey=51e2488cb9744482ac40ab958a9bd4b3")
    fun getMediaFeeds(
        @Query("page") page: Int = 1,
        @Query("pageSize") count: Int = 10
    ): Call<ApiResponse<List<ArticlesItem>>>

    // 3113853757.1677ed0.035f3e26d40745b2957ea09af1429049
    // 5415976503.1677ed0.c1969a92489940fbac11608b34bf8129
    //https://graph.instagram.com/v7.0/17841405822304914/insights?metric=impressions,reach,profile_views&period=day&access_token=3113853757.1677ed0.035f3e26d40745b2957ea09af1429049'
    @GET
    fun getInstaFeeds(
        @Url url :String=""
    ): Call<ApiResponse<List<InstaFeed>>>

    @GET("/v2/everything")
    fun fetchFeedList(
        @retrofit2.http.Query("q") q: String?,
        @retrofit2.http.Query("apiKey") apiKey: String?,
        @retrofit2.http.Query("page") page: Long,
        @retrofit2.http.Query("pageSize") pageSize: Int
    ): Call<FeedResponse>
}