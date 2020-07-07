package com.blog.addblog

import androidx.lifecycle.MutableLiveData
import com.gwl.MyApplication
import com.gwl.model.BlogPostResponse
import com.gwl.model.PostBlogRequest
import com.gwl.networking.client.server.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author GWL
 */
class AddBlogRepository(private val api: NetworkAPI) {
    private val blogDao by lazy { MyApplication.database.blogDao() }

    // region - Public functions
    suspend fun addBlog(
        blogRequest: PostBlogRequest,
        onBlogPosted: MutableLiveData<Boolean>
    ) {
        api.postBlog(blogRequest).enqueue(object :
            Callback<BlogPostResponse> {
            override fun onResponse(
                call: Call<BlogPostResponse>, response: Response<BlogPostResponse>
            ) {
                onBlogPosted.postValue(true)
                response.body()?.also {
                    GlobalScope.launch(Dispatchers.IO) {
                        blogDao.add(it)
                    }
                }
            }

            override fun onFailure(call: Call<BlogPostResponse>, t: Throwable) {
                onBlogPosted.postValue(false)
            }
        })
    }
// endregion
}