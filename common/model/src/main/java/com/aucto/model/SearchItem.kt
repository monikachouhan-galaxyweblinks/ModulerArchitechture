package com.aucto.model

import android.os.Parcelable
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