package com.aucto.cache.db.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.aucto.model.*

// * Created on 19/9/19.
/**
 * @author GWL
 */
class CustomTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun convertStringListToObject(entityProperty: List<String>?): String? {
        if (entityProperty == null) return ""
        if (entityProperty.isEmpty()) return ""
        val builder = StringBuilder()
        entityProperty.forEach { builder.append(it).append(",") }
        builder.deleteCharAt(builder.length - 1)
        return builder.toString()
    }


    @TypeConverter
    fun syncStatusToInt(downloadStatus: SyncState?): String {
        return downloadStatus?.name ?: SyncState.SUCCESS.name
    }

    @TypeConverter
    fun syncIntToStatus(downloadStatus: String?): SyncState {
        return if (downloadStatus != null) SyncState.valueOf(downloadStatus)
        else SyncState.FAIL
    }

    @TypeConverter
    fun convertToComments(entityProperty: Comments?): String? {
        return gson.toJson(entityProperty)
    }

    @TypeConverter
    fun convertToImages(entityProperty: Images?): String? {
        return gson.toJson(entityProperty)
    }

    @TypeConverter
    fun convertToLocation(entityProperty: Location?): String? {
        return gson.toJson(entityProperty)
    }

    @TypeConverter
    fun convertToLikes(entityProperty: Likes?): String? {
        return gson.toJson(entityProperty)
    }

    @TypeConverter
    fun convertToVideos(entityProperty: Videos?): String? {
        return gson.toJson(entityProperty)
    }


    @TypeConverter
    fun convertToInsta(entityProperty: InstaUser?): String? {
        return gson.toJson(entityProperty)
    }

    @TypeConverter
    fun convertToEntityProperty(databaseValue: String?): Comments? {
        return databaseValue?.let { gson.fromJson(it, Comments::class.java) }
    }

    @TypeConverter
    fun convertToImagesProperty(databaseValue: String?): Images? {
        return databaseValue?.let { gson.fromJson(it, Images::class.java) }
    }

    @TypeConverter
    fun convertToLocationProperty(databaseValue: String?): Location? {
        return databaseValue?.let { gson.fromJson(it, Location::class.java) }
    }

    @TypeConverter
    fun convertToLikesProperty(databaseValue: String?): Likes? {
        return databaseValue?.let { gson.fromJson(it, Likes::class.java) }
    }

    @TypeConverter
    fun convertToVideosProperty(databaseValue: String?): Videos? {
        return databaseValue?.let { gson.fromJson(it, Videos::class.java) }
    }

    @TypeConverter
    fun convertToInstaUserProperty(databaseValue: String?): InstaUser? {
        return databaseValue?.let { gson.fromJson(it, InstaUser::class.java) }
    }


    @TypeConverter
    fun convertImageListToJson(list: List<CarouselImage>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<CarouselImage>>() {
        }.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun convertJsonToImageList(json: String): List<CarouselImage>? {
        val gson = Gson()
        val type = object : TypeToken<List<CarouselImage>>() {

        }.type
        return gson.fromJson<List<CarouselImage>>(json, type)
    }

}