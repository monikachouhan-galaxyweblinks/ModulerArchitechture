package com.aucto.networking.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthHeaderInterceptor(private val context: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!request.url().encodedPath().endsWith("signup") ||
            !request.url().encodedPath().endsWith("check") ||
            !request.url().encodedPath().endsWith("checkprovider") ||
            !request.url().encodedPath().endsWith("login")
        ) {
            request = request.newBuilder()
                .build()
        }

        return chain.proceed(request)
    }
}
