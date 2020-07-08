package com.aucto.core.sync

import android.content.Context
import androidx.work.*
import com.aucto.model.PostBlogRequest
import com.google.gson.Gson

object SyncManager {

    fun <T : ListenableWorker> sync(context: Context, data: Data, workerClass: Class<T>) {
        val workManager = WorkManager.getInstance(context)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val request = OneTimeWorkRequest
            .Builder(workerClass)
            .setConstraints(constraints)
            .setInputData(data)
            .addTag("sync").build()
        workManager.enqueue(request)
    }

    fun createPost(context: Context, request: PostBlogRequest, localDBId: Int) {
        val dataBuilder = Data.Builder()
        dataBuilder.putInt(KEY_DATA_DB_ID,  localDBId)
        dataBuilder.putString(KEY_DATA_TO_SYNC, Gson().toJson(request))
        val data = dataBuilder.build()
        sync(context, data, BlogSyncWorker::class.java)
    }

    /* fun sync(context: Context) {
         val workManager = WorkManager.getInstance(context)
         // workManager.cancelAllWork()//AllWorkByTag("sync").
         workManager.cancelAllWorkByTag("sync") // AllWorkByTag("sync").

         val constraints = Constraints.Builder()
             .setRequiredNetworkType(NetworkType.CONNECTED)
             .build()
         val request = OneTimeWorkRequest
             .Builder(SyncWorkManager::class.java)
             .setConstraints(constraints)
             .addTag("sync").build()
         workManager.enqueue(request)
     }

 */
}