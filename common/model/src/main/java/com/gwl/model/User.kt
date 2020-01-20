package com.gwl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author GWL
 */
@Entity
class User {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String = "test"
    var password: String = ""
}