package com.aucto.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticlesItem(

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
    val content: String? = null,
    var type: MediaType? = MediaType.VIDEO,
    var sourceUrl: String? = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
) : Parcelable