package com.gwl.cache.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gwl.model.InstaFeed
import com.gwl.model.User

@Dao
interface FeedDao {

    @Update
    suspend fun update(user: InstaFeed): Int

    @Delete
    suspend fun delete(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(syncItems: List<User>)

    @Query("SELECT * FROM user")
    fun getAllUserList(): LiveData<List<User>>
}