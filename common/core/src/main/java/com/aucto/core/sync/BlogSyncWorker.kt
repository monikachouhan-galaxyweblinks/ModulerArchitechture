package com.aucto.core.sync

import android.content.Context
import androidx.work.WorkerParameters
import com.aucto.cache.db.AppDatabase
import com.aucto.model.BlogPostResponse
import com.aucto.model.PostBlogRequest
import com.aucto.model.SyncState
import com.aucto.networking.result.APIError
import com.aucto.networking.result.APIResult
import com.google.gson.Gson


class BlogSyncWorker(context: Context, workerParams: WorkerParameters) :
    SyncWorkManager<PostBlogRequest, BlogPostResponse>(context, workerParams) {
    val database by lazy { AppDatabase.getInstance(context) }

    val bloagDao = database.blogDao()

    override fun getData(): PostBlogRequest {
        return Gson().fromJson(
            inputData.getString(KEY_DATA_TO_SYNC),
            PostBlogRequest::class.java
        )
    }

    override suspend fun performTask(request: PostBlogRequest): APIResult<BlogPostResponse> {
        return networkAPI.postNewBlog(request)
    }

    override suspend fun onSuccess(result: BlogPostResponse?, id: Int) {
        bloagDao.update(state = SyncState.SUCCESS, id = id)

    }

    override fun onFailure(result: APIError?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}