package com.gwl.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @author GWL
 */
@Parcelize
class SearchItem : Parcelable {
    var id: Int = 0
    var userId: String = "test"
    var title: String = ""
    var body: String = ""
}