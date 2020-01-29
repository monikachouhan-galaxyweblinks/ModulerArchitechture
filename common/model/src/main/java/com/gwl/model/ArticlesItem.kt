package com.gwl.model

import com.google.gson.annotations.SerializedName

abstract class ArticlesItem(

    @SerializedName("publishedAt")
    val publishedAt: String? = null,

    @SerializedName("author")
    val author: String? = null,

    @SerializedName("urlToImage")
    val urlToImage: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("source")
    val source: Source? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("content")
    val content: String? = null
) {
    abstract var type: MediaType?
    abstract var sourceUrl: String?
}