package com.aucto.networking.response

import com.google.gson.annotations.SerializedName

sealed class BaseResponse<T> {
    abstract fun isSuccessful(): Boolean
    open fun isSyncAvailabel(): Boolean = false
}

/**
 * Generic response for the Pilgrim API. Used in most API calls.
 *
 * The "data" field will contain the actual response data
 *
 * The "Message" field will contain a useful message for the user, if applicable
 *
 * The "isPaginated" field tells us whether this call is paginated
 *
 * The "isLast" field tells us whether this is the last page of a paginated call
 *
 * The "error" field should only exist for a failed call, and contains an error string
 * Additional information will be contained in a more specific field, e.g., "username" or "password"
 * which will denote the specific problem
 */

const val HTTP_SUCCESS_CODE = "Success"

open class ApiResponse<T>(
    @SerializedName("data")
    var data: T?,

    @SerializedName("articles")
    var articles: T?,

    @SerializedName("isPaginated")
    private var isPaginated: Boolean? = true,

    @SerializedName("isLast")
    private var isLast: Boolean?,

    @SerializedName("message")
    var message: String? = "",

    @SerializedName("code")
    var code: String? = "",

    @SerializedName("count")
    var itemCount: Int? = 0,
    //Make it true when data successfully add on network
    val isSynced: Boolean = false
) : BaseResponse<T>() {
    override fun isSuccessful(): Boolean {
        return data != null || code == HTTP_SUCCESS_CODE
    }

    override fun isSyncAvailabel(): Boolean {
        return true
    }

    fun hasNextPage(): Boolean {
        return if (isPaginated == null || isPaginated == false) {
            false
            //true
        } else {
            isLast == false
        }
    }
}