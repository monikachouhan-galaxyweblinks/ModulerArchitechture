package com.aucto.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Likes(
    @SerializedName("count")
    var count: Int
) : Parcelable