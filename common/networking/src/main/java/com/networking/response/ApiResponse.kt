package com.pilgrimnetworking.response

import com.google.gson.annotations.SerializedName

sealed class PilgrimResponse<T> {
    abstract fun isSuccessful(): Boolean
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
    var data: T,

    @SerializedName("isPaginated")
    private var isPaginated: Boolean?,

    @SerializedName("isLast")
    private var isLast: Boolean?,

    @SerializedName("message")
    var message: String,

    @SerializedName("code")
    var code: String
) : PilgrimResponse<T>() {
    override fun isSuccessful(): Boolean {
        return data != null || code == HTTP_SUCCESS_CODE
    }

    fun hasNextPage(): Boolean {
        return if (isPaginated == null || isPaginated == false) {
            false
        } else {
            isLast == false
        }
    }
}