package com.aucto.networking.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.util.*

class NetworkHeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("platform", "android")
            .addHeader("locale", Locale.getDefault().language)
            .addHeader("timezone", TimeZone.getDefault().id)
            .addHeader("X-Api-Key","")
            .addHeader("cid","")
            .build()
        return chain.proceed(request)
    }
}
