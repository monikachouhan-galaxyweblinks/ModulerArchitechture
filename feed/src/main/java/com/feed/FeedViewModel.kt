package com.feed

import android.util.Log
import androidx.databinding.ObservableField
import com.gwl.core.BaseViewModel
import com.gwl.model.FeedResponse
import com.networking.client.server.NetworkAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedViewModel(val pilgrimAPI: NetworkAPI) : BaseViewModel() {

    val feedResponse: ObservableField<FeedResponse> by lazy { ObservableField<FeedResponse>() }
    var adapter:  FeedAdapter = FeedAdapter()
    fun getList() {
        GlobalScope.launch(Dispatchers.IO) {
            pilgrimAPI.getFeedList().enqueue(object : Callback<FeedResponse?> {
                override fun onFailure(call: Call<FeedResponse?>, t: Throwable) {
                    Log.d("FeedViewModel", "on Failure ${t.message}")

                }

                override fun onResponse(call: Call<FeedResponse?>, response: Response<FeedResponse?>) {
                    Log.d("FeedViewModel", " on success ${response.body()}")
                    feedResponse.set(response.body())
                }

            })
        }

    }
}