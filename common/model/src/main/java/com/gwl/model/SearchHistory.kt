package com.gwl.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @author GWL
 */
@Parcelize
@Entity
data class SearchHistory(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var history: String
) : Parcelable