package com.aucto.networking.client.server

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.aucto.model.*
import com.aucto.networking.R
import com.aucto.networking.response.ApiResponse
import com.aucto.networking.result.APIError
import com.aucto.networking.result.APIErrorType
import com.aucto.networking.result.APIResult
import com.aucto.networking.service.AuthenticationService
import com.aucto.networking.util.StringUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import ru.gildor.coroutines.retrofit.Result
import ru.gildor.coroutines.retrofit.awaitResult
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*


class NetworkAPI(retrofit: Retrofit) : NetworkAPIContract() {

    private val authService = retrofit.create(AuthenticationService::class.java)
    private val decoder = Gson()


    override suspend fun postBlog(request: PostBlogRequest): Call<BlogPostResponse> {
        return authService.postBlog(signupUser = request)
    }

    override suspend fun postNewBlog(request: PostBlogRequest): APIResult<BlogPostResponse> {
        return  authService.postNewBlog(signupUser = request).apiResult()
    }
    override suspend fun getList(): Call<ApiResponse<ResponseData>> {
        return authService.getList()
    }

    override suspend fun getFeedList(page: Long, count: Int): APIResult<List<ArticlesItem>> {
        return authService.fetchFeedList("bitcoin", "5b0c09c541454cad8d8626d779802b2e", page, count)
            .feedApiResult()
    }

    override suspend fun getPlayerFeeds(page: Int, count: Int): APIResult<List<ArticlesItem>> {
        return authService.getMediaFeeds(page, count).apiResult()
    }

    override suspend fun getInstaFeeds(token :String): APIResult<List<InstaFeed>> {
        return authService.getInstaFeeds("https://api.instagram.com/v1/users/self/media/recent/?access_token=$token").apiResult()
    }
/*  suspend fun getFeeds(page: Long, count: Int): Call<FeedResponse> {
        return authService.fetchFeedList("bitcoin", "5b0c09c541454cad8d8626d779802b2e", page, count)
    }*/

    // region - Helpers
    private suspend fun Call<ApiResponse<Unit>>.emptyBodyResult(): APIResult<Unit> {
        return try {
            awaitResult()
            APIResult.Success(Unit)
        } catch (error: Throwable) {
            if (error is NullPointerException) {
                APIResult.Success(Unit)
            } else {
                APIResult.Failure(errorFromAnyException(error))
            }
        }
    }

    private suspend inline fun Call<ApiResponse<Unit>>.executeEmptyResponseCallAsync(): APIResult<Unit> {
        //Timber.v("Executing empty response call async")
        var error: APIResult<Unit>? = null
        val result: Response<ApiResponse<Unit>>? = GlobalScope.async {
            try {
                this@executeEmptyResponseCallAsync.execute()
            } catch (exception: Throwable) {
                error = when (exception) {
                    is HttpException ->
                        APIResult.Failure(errorFromHttpException(exception))
                    else -> {
                        APIResult.Failure(errorFromAnyException(exception))
                    }
                }
                null
            }
        }.await()

        return if (result?.isSuccessful == true) {
            APIResult.Success(Unit)
        } else {
            APIResult.Failure(
                APIError(
                    hashMapOf(), APIErrorType.General, result?.code()
                        ?: -1, null, StringUtil.getString(R.string.default_api_error)
                )
            )
        }
    }

    private suspend fun <T : Any> Call<T>.mappedApiResult(): APIResult<T> {
        val result = awaitResult()
        return when (result) {
            is Result.Ok -> APIResult.Success(ApiResponse(result.value, result.value, null, null))
            is Result.Error -> APIResult.Failure(errorFromHttpException(result.exception))
            is Result.Exception -> APIResult.Failure(errorFromAnyException(result.exception))
        }
    }

    // region - Response Parsing
    private suspend fun <T> Call<ApiResponse<T>>.apiResult(): APIResult<T> {
        val result = awaitResult()
        Log.d("apiResult", " apiResult $result")
        return when (result) {
            is Result.Ok -> {
                Log.d("apiResult", " apiResult success " + result.value.data)
                APIResult.Success(result.value)
            }
            is Result.Error -> APIResult.Failure(errorFromHttpException(result.exception))
            is Result.Exception -> APIResult.Failure(errorFromAnyException(result.exception))
        }
    }
    // endregion

    // region - Response Parsing
    private suspend fun Call<FeedResponse>.feedApiResult(): APIResult<List<ArticlesItem>> {
        val result = awaitResult()
        // Log.d("apiResult", " apiResult feedApiResult $result")
        return when (result) {
            is Result.Ok -> {
                Log.d("apiResult", " apiResult success " + result.value.articles)
                APIResult.Success(result.value.articles)
            }
            is Result.Error -> APIResult.Failure(errorFromHttpException(result.exception))
            is Result.Exception -> APIResult.Failure(errorFromAnyException(result.exception))
        }
    }
    // endregion

    // region - Error handling
    private fun errorFromHttpException(exception: HttpException): APIError {
        val errorBody = exception.response().errorBody()?.string()
        val code = exception.code()
        val message = exception.message()

        return if (errorBody != null) {
            if (JSONTokener(errorBody).nextValue() !is JSONObject)
                return APIError(mapOf(), APIErrorType.General, code, exception, message)

            val errors: HashMap<String, Any?> = decoder.fromJson(errorBody, errorMapType)
            val stringError: HashMap<String, String> = HashMap(errors.mapValues {
                it.value?.toString() ?: ""
            })
            val type = errorTypeFrom(stringError)
            val returnedMessage = messageFrom(stringError) ?: message
            val errorResult = APIError(stringError, type, code, exception, returnedMessage)
            errorResult
        } else {
            APIError(mapOf(), APIErrorType.General, code, exception, message)
        }
    }

    private fun errorFromAnyException(exception: Throwable): APIError {
        val (typeString, reason) = when (exception) {
            is SocketTimeoutException,
            is SocketException,
            is UnknownHostException -> Pair("Network", "Network error")
            else -> Pair("NetworkCall", "An error occurred")
        }
        return APIError(
            hashMapOf(typeString to reason),
            APIErrorType.Network(exception),
            -1,
            exception,
            reason
        )
    }

    private fun errorTypeFrom(errors: HashMap<String, String>): APIErrorType {
        /*
         * In APIClient there were Exception handling for subscription
         * for make same handling, return  APIErrorType.Subscription.
        */
        if (errors.contains("firstName") ||
            errors.contains("lastName") ||
            errors.contains("email") ||
            errors.contains("password1") ||
            errors.contains("password2") ||
            errors.contains("country")
        ) {
            return APIErrorType.SignUpError
        }
        return APIErrorType.General
    }

    private fun messageFrom(errors: HashMap<String, String>): String? {
        return errors.values.first()
    }

    private val errorMapType = object : TypeToken<HashMap<String, Any>>() {}.type
    // endregion
}

