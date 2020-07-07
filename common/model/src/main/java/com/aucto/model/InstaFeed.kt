package com.aucto.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class InstaFeed(
/* @SerializedName("caption")*/

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
    var images: Images?,
    @SerializedName("location")
    var location: Location?,
    @SerializedName("carousel_media")
    var carosel: List<CarouselImage>? = listOf(),
    @SerializedName("videos")
    var videos: Videos?,
    @SerializedName("likes")
    var likes: Likes,
    @SerializedName("link")
    var link: String,
    @SerializedName("type")
    var type: String,
    @SerializedName("user")
    var instaUser: InstaUser,
    @SerializedName("user_has_liked")
    var userHasLiked: Boolean = false,
    var isLiked: Boolean = false
) : Parcelable {
    var postcaption: String = arrayOf(
        "When you can't find the sunshine, be the sunshine.",
        "Set goals you don’t tell anyone about. Achieve them. Then give yourself the highest of fives!",
        "The happiest people don't have the best of everything, they make the best of everything.",
        "Every day may not be good but there's good in every day.",
        "Handle every situation like a dog. If you can't eat it or play with it, just pee on it and walk away.",
        "I never make the same mistake twice. I make it like five or six times, you know, just to be sure."
    ).random()

    fun getFormattedLikes(): String {
        return "${likes.count} \n Likes"
    }

    fun getFormattedComments(): String {
        return "${comments.count} \n Comments"
    }

    fun getFormattedViews(): String {
        return "0 \n Views"
    }
}