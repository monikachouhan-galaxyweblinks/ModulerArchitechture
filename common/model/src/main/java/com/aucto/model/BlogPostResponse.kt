package com.aucto.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Entity(tableName = "blogs")
@Parcelize
data class BlogPostResponse(
    @PrimaryKey(autoGenerate = true)
    var blogId: Int = 0,
    @SerializedName("body")
    var body: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("userId")
    var userId: Int,
    val dbId :Int = Random.nextInt(0, 100),
    var isSynced: Boolean = true
) : Parcelable, Syncable {
    override var syncStatus: SyncState = SyncState.PENDING
}