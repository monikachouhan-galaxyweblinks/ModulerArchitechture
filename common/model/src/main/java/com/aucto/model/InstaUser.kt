package com.aucto.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InstaUser(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("profile_picture")
    val profilePicture: String,
    @SerializedName("username")
    val username: String
)  : Parcelable
