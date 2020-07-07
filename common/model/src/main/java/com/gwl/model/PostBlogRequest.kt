package com.gwl.model

import com.google.gson.annotations.SerializedName


data class PostBlogRequest(
    @SerializedName("title")
    var title: String,
    @SerializedName("body")
    var body: String,
    @SerializedName("userId")
    var userId: Int

) {

    constructor() : this("", "", 0)
}