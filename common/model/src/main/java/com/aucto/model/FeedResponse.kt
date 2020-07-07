package com.aucto.model

import com.google.gson.annotations.SerializedName

data class FeedResponse(

    @SerializedName("totalResults")
    val totalResults: Int? = null,

    @SerializedName("articles")
    val articles: List<ArticlesItem> = listOf(),

    @SerializedName("status")
    val status: String? = null
)