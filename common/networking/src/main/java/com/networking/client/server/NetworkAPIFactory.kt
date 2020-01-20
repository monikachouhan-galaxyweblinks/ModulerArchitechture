package com.networking.client.server

import android.content.Context
import com.networking.BuildConfig
import com.networking.client.NullOnEmptyConverterFactory
import com.networking.client.UniversalConverter
import com.networking.interceptors.AuthHeaderInterceptor
import com.networking.interceptors.NetworkHeaderInterceptor
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.*
import java.util.concurrent.TimeUnit


object NetworkAPIFactory {
    fun standardClient(context: Context): NetworkAPI {
        val authInterceptor = AuthHeaderInterceptor(context)
        val headerInterceptor = NetworkHeaderInterceptor()

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        val interceptors = ArrayList<Interceptor>(3)
      //  interceptors.add(authInterceptor)
        interceptors.add(headerInterceptor)
        interceptors.add(loggingInterceptor)
//        return createClient(interceptors, "https://api.androidhive.info/json/", 60)
        return createClient(interceptors, "https://newsapi.org", 60)
    }

    private fun createClient(
        interceptors: List<Interceptor>, baseUrl: String, timeoutSeconds: Long
    ): NetworkAPI {
        val okClientBuilder = UnsafeOkHttpClient.unsafeOkHttpClient

        interceptors.forEach {
            okClientBuilder.addInterceptor(it)
        }

        val okClient = okClientBuilder
            .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(UniversalConverter())
            .client(okClient)
            .build()

        return NetworkAPI(retrofit)
    }
}