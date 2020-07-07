package com.aucto.addblog

import androidx.lifecycle.MutableLiveData
import com.aucto.MyApplication
import com.aucto.core.sync.SyncManager
import com.aucto.model.BlogPostResponse
import com.aucto.model.PostBlogRequest
import com.aucto.networking.client.server.NetworkAPI

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
        val response = BlogPostResponse(
            body = blogRequest.body,
            title = blogRequest.title,
            userId = blogRequest.userId
        )
        blogDao.add(response)
        SyncManager.createPost(MyApplication.instance, blogRequest, response.dbId)
    }
// endregion
}