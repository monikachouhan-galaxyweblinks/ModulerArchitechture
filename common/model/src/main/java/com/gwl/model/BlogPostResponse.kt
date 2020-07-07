package com.gwl.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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
    @SerializedName("id")
    var id: Int,
    var isSynced: Boolean = true
) : Parcelable