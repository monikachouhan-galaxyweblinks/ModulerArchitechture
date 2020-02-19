package com.gwl.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gwl.cache.db.dao.CustomTypeConverter
import com.gwl.cache.db.dao.FavoriteDao
import com.gwl.cache.db.dao.FeedDao
import com.gwl.cache.db.dao.UserDao
import com.gwl.model.FavoriteFeed
import com.gwl.model.InstaFeed
import com.gwl.model.User

/**
 *The Room database for this app
 */

@TypeConverters(CustomTypeConverter::class)
@Database(entities = [User::class, InstaFeed::class, FavoriteFeed::class], version = 5, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun feedDao(): FeedDao
    abstract fun favDao(): FavoriteDao

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