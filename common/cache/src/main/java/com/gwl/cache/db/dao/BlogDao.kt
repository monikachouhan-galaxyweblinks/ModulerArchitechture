package com.gwl.cache.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gwl.model.BlogPostResponse

@Dao
interface BlogDao {

    @Update
    suspend fun update(blog: BlogPostResponse): Int

    @Insert
    suspend fun add(blog: BlogPostResponse)

    @Delete
    suspend fun delete(blog: BlogPostResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBlogList(blogs: List<BlogPostResponse>)

    @Query("SELECT * FROM blogs")
    fun getAllBlogList(): LiveData<List<BlogPostResponse>>
}