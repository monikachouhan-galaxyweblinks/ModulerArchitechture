package com.aucto.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @author GWL
 */
@Parcelize
@Entity
class User : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = "test"
    var city: String = ""
    var state: String = ""
    var mobile: String = ""
    var email: String = ""
    var profileUrl: String = ""
    var password: String = ""
}