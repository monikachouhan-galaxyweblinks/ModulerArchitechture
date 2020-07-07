package com.aucto.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * @author GWL
 */
@Parcelize
@Entity(indices =  [Index(value = ["history"], unique = true)])
data class SearchHistory(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var history: String
) : Parcelable
{
    override fun toString(): String {
        return "SearchHistory(id=$id, history='$history')"
    }

}