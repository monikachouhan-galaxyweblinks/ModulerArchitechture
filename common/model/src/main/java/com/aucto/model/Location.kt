package com.aucto.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: Long
) : Parcelable