package com.gwl.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InstaFeed(
/* @SerializedName("caption")
    val caption: Any?,*/
    @SerializedName("comments")
    val comments: Comments,
    @SerializedName("created_time")
    val createdTime: String,
    @SerializedName("filter")
    val filter: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("images")
    val images: Images,
    @SerializedName("location")
    val location: Location?,
    @SerializedName("carousel_media")
    val carosel: List<CarouselImage>?,
    @SerializedName("videos")
    val videos: Videos,
    @SerializedName("likes")
    val likes: Likes,
    @SerializedName("link")
    val link: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("user")
    val instaUser: InstaUser,
    @SerializedName("user_has_liked")
    val userHasLiked: Boolean
) : Parcelable