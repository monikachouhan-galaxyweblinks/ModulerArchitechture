package com.gwl.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class InstaFeed(
/* @SerializedName("caption")
    var caption: Any?,*/
    @PrimaryKey(autoGenerate = true)
    var itemId: Int = 0,
    @SerializedName("comments")
    var comments: Comments,
    @SerializedName("created_time")
    var createdTime: String,
    @SerializedName("filter")
    var filter: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("images")
    var images: Images,
    @SerializedName("location")
    var location: Location?,
    @SerializedName("carousel_media")
    var carosel: List<CarouselImage>?,
    @SerializedName("videos")
    var videos: Videos,
    @SerializedName("likes")
    var likes: Likes,
    @SerializedName("link")
    var link: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("user")
    var instaUser: InstaUser,
    @SerializedName("user_has_liked")
    var userHasLiked: Boolean,
    var isLiked: Boolean = false
) : Parcelable