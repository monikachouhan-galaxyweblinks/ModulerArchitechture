package com.aucto.core.sync

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.aucto.networking.client.server.NetworkAPI
import com.aucto.networking.client.server.NetworkAPIFactory
import com.aucto.networking.result.APIError
import com.aucto.networking.result.APIResult

/**
 * @author GWL
 */
val KEY_DATA_TO_SYNC: String = "SyncData"
val KEY_DATA_DB_ID: String = "SyncDataId"

abstract class SyncWorkManager<REQ, RES>(context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    val networkAPI: NetworkAPI by lazy { NetworkAPIFactory.standardClient(context) }

    abstract suspend fun performTask(request: REQ): APIResult<RES>
    abstract fun getData(): REQ
    abstract suspend fun onSuccess(result: RES?, id: Int)
    abstract fun onFailure(result: APIError?)

    // region - Override function
    override suspend fun doWork(): Result {
        try {
            val data = getData() //inputData.getParcelable() as REQ
            Log.e("SyncWorkManager", " performTask called before $data")
            val result = performTask(data)
            Log.e("SyncWorkManager", " performTask called $result")
            if (result is APIResult.Failure) {
                Result.retry()
                Log.e("SyncWorkManager", " performTask RETY ${result.details}")

            }
            handleResponse(result)
        } catch (e: Exception) {
            Log.e("SyncWorkManager", e.toString())
            return Result.retry()
        }
        return Result.success()
    }
    // endregion

    suspend fun handleResponse(response: APIResult<RES>) {
        when (response) {
            is APIResult.Success -> {
                val id = inputData.getInt(KEY_DATA_DB_ID, -1)
                onSuccess(response.response.data, id)
            }
            is APIResult.Failure -> {
                onFailure(response.error)
            }
        }
    }

}

val Data.parcelables by lazy {
    mutableMapOf<String, Parcelable>()
}

fun Data.putParcelable(key: String, parcelable: Parcelable): Data {
    parcelables[key] = parcelable
    // to allow for chaining
    return this
}

// in order to get value in the Work class created  Wrok.doWork method
fun Data.getParcelable(key: String): Parcelable? = parcelables[key]
