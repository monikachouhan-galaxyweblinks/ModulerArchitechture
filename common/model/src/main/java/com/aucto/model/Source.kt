package com.aucto.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("id")
    val id: String? = null
) : Parcelable