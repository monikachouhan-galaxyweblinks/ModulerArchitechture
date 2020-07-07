package com.aucto.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FavoriteFeed(
/* @SerializedName("caption")
    var caption: Any?,*/
    @PrimaryKey
    var likedId: String = "",
    var position: Int = -1,
    var count: Int = 0,
    var isLiked: Boolean = true
) : Parcelable, Syncable {
    override var syncStatus: SyncState = SyncState.PENDING
}