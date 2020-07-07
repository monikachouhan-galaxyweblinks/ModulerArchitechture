package com.aucto.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Videos(
    @SerializedName("low_resolution")
    val lowResolution: LowResolution,
    @SerializedName("standard_resolution")
    val standardResolution: StandardResolution
) : Parcelable