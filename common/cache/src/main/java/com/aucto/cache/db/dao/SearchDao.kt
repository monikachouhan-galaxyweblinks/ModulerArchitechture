package com.aucto.cache.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aucto.model.SearchHistory

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(history: SearchHistory)

    @Update
    suspend fun update(history: SearchHistory): Int

    @Delete
    suspend fun delete(history: SearchHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(histories: List<SearchHistory>)

    @Query("SELECT * FROM searchhistory  ORDER BY id DESC LIMIT 5")
    fun getAllSearchHistory(): LiveData<List<SearchHistory>>

    @Query("SELECT * FROM searchhistory ORDER BY id DESC LIMIT 5")
    fun getSearchHistories(): List<SearchHistory>

    @Query("SELECT * FROM searchhistory WHERE history  like '%' || (:search)  || '%' LIMIT 5")
    fun getAllSearchHistories(search:String): List<SearchHistory>


}