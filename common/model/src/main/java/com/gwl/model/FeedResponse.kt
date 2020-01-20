package com.gwl.model

import com.google.gson.annotations.SerializedName

data class FeedResponse(

	@SerializedName("totalResults")
	val totalResults: Int? = null,

	@SerializedName("articles")
	val articles: List<com.gwl.model.ArticlesItem?>? = null,

	@SerializedName("status")
	val status: String? = null
)