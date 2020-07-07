package com.aucto.cache.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.aucto.model.BlogPostResponse
import com.aucto.model.SyncState

@Dao
interface BlogDao {

    @Query("UPDATE blogs SET syncStatus = :state WHERE dbId = :id")
    suspend fun update(id:Int, state: SyncState = SyncState.SUCCESS): Int

    @Insert
    suspend fun add(blog: BlogPostResponse)

    @Delete
    suspend fun delete(blog: BlogPostResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBlogList(blogs: List<BlogPostResponse>)

    @Query("SELECT * FROM blogs")
    fun getAllBlogList(): LiveData<List<BlogPostResponse>>

    @Query("SELECT * FROM blogs where syncStatus == :state")
    fun getAllSuccessBlogList(state: SyncState = SyncState.SUCCESS): LiveData<List<BlogPostResponse>>
}