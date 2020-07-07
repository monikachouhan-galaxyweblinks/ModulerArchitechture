package com.aucto.cache.db.dao

import androidx.room.*
import com.aucto.model.FavoriteFeed

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(fav: FavoriteFeed)

    /*   @Update
       suspend fun update(user: FavoriteFeed): Int*/

    @Query("DELETE FROM favoritefeed where likedId=:id")
    suspend fun delete(id: String)

    @Delete
    suspend fun delete(feed: FavoriteFeed)
/*
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(syncItems: List<FavoriteFeed>)*/

    @Query("SELECT * FROM favoritefeed")
    fun getAllFavList(): List<FavoriteFeed>

    @Query("SELECT * FROM favoritefeed where likedId =:id")
    fun getFavById(id: String): FavoriteFeed?
}