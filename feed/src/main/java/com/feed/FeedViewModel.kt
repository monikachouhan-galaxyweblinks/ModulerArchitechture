package com.feed

import com.gwl.core.BaseViewModel
import com.networking.client.server.NetworkAPI
import com.gwl.model.ResponseData
import com.pilgrimnetworking.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedViewModel(val loginRepository: FeedRepository,val pilgrimAPI: NetworkAPI) : BaseViewModel() {
     fun getList(){
         GlobalScope.launch(Dispatchers.IO) {
             pilgrimAPI.getList().enqueue(object : Callback<ApiResponse<ResponseData>> {
                 override fun onFailure(call: Call<ApiResponse<ResponseData>>, t: Throwable) {
                 }

                 override fun onResponse(
                     call: Call<ApiResponse<ResponseData>>,
                     response: Response<ApiResponse<ResponseData>>
                 ) {
                 }

             })
         }

    }
}