package com.aucto.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostBlogRequest(
    @SerializedName("title")
    var title: String,
    @SerializedName("body")
    var body: String,
    @SerializedName("userId")
    var userId: Int

) : Parcelable{

    constructor() : this("", "", 0)


    /* override fun data(): PostBlogRequest {
        return this
    }*/
}