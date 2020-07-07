package com.aucto.networking.result

import com.aucto.networking.R
import com.aucto.networking.response.ApiResponse
import com.aucto.networking.response.HTTP_SUCCESS_CODE
import com.aucto.networking.util.StringUtil.getString

sealed class APIResult<T> {
    class Failure<T>(val details: APIError) : APIResult<T>()
    class Success<T>(val response: ApiResponse<T>) : APIResult<T>() {
        constructor(value: T) : this(ApiResponse(value, value, null, null, "", HTTP_SUCCESS_CODE))
    }

    val value: ApiResponse<T>?
        get() = (this as? Success)?.response

    val error: APIError?
        get() = (this as? Failure)?.details

    fun hasNextPage(): Boolean {
        return when (this) {
            is Success -> this.response.hasNextPage()
            is Failure -> false
        }
    }

    fun <U> map(apply: (T?) -> U): APIResult<U> {
        return when (this) {
            is Success -> {
                val data = this.response.data
                val transformed = apply(data)
                Success(
                    ApiResponse(
                        transformed,
                        transformed,
                        this.response.hasNextPage(),
                        !this.response.hasNextPage(),
                        this.response.message,
                        this.response.code
                    )
                )
            }
            is Failure -> {
                Failure(this.details)
            }
        }
    }

    fun <U> mapArticles(apply: (T?) -> U): APIResult<U> {
        return when (this) {
            is Success -> {
                val data = this.response.articles
                val transformed = apply(data)
                Success(
                    ApiResponse(
                        transformed,
                        transformed,
                        this.response.hasNextPage(),
                        !this.response.hasNextPage(),
                        this.response.message,
                        this.response.code
                    )
                )
            }
            is Failure -> {
                Failure(this.details)
            }
        }
    }

    fun withFailure(failure: (APIError) -> Unit): APIResult<T> {
        if (this is Failure) {
            failure(this.details)
        }
        return this
    }
}

sealed class APIErrorType {
    object General : APIErrorType()
    object SessionExpired : APIErrorType()
    object CallNotTried : APIErrorType()
    object InvalidResponse : APIErrorType()
    object NoVideoAvailable : APIErrorType()
    object Purchase : APIErrorType()
    object SignUpError : APIErrorType()
    class Network(val throwable: Throwable) : APIErrorType()
}

data class APIError(
    val errors: Map<String, String>,
    val type: APIErrorType,
    val code: Int,
    val thrownCause: Throwable?,
    override val message: String
) : Exception(message) {
    companion object {
        val notTried = APIError(
            hashMapOf(), APIErrorType.CallNotTried, -1, null, getString(R.string.not_implemented)
        )
        val wrongJsonResponse = APIError(
            hashMapOf(), APIErrorType.InvalidResponse, -1, null,
            getString(R.string.invalid_response)
        )
        val notImplemented = APIError(
            hashMapOf(), APIErrorType.General, -1, null, getString(R.string.not_implemented)
        )
        val default = APIError(
            hashMapOf(), APIErrorType.General, -1, null, getString(R.string.default_api_error)
        )
    }
}