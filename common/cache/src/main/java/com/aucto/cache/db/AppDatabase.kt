package com.aucto.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aucto.cache.db.dao.*
import com.aucto.model.*
/**
 *The Room database for this app
 */

@TypeConverters(CustomTypeConverter::class)
@Database(
    entities = [User::class, InstaFeed::class, FavoriteFeed::class, SearchHistory::class , BlogPostResponse::class],
    version = 10,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun feedDao(): FeedDao
   // abstract fun syncDao(): SyncRequestDao<Syncable>
    abstract fun favDao(): FavoriteDao
    abstract fun searchDao(): SearchDao
    abstract fun blogDao(): BlogDao

    companion object {

        private const val DATABASE_NAME = "app-db"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration().build()
        }
    }
}