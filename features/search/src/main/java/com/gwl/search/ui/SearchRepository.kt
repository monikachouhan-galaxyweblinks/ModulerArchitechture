package com.gwl.search.ui

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gwl.MyApplication
import com.gwl.cache.db.dao.SearchDao
import com.gwl.model.SearchHistory
import com.gwl.model.SearchItem
import java.io.IOException

// * Created on 16/3/20.
/**
 * @author GWL
 */
class SearchRepository {
    val gson = Gson()
    val searchDao: SearchDao by lazy { MyApplication.database.searchDao() }

    fun getJsonDataFromAsset(fileName: String = "search_mockData.json"): List<SearchItem> {
        val jsonString: String
        try {
            jsonString =
                MyApplication.instance.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return listOf()
        }
        val listPersonType = object : TypeToken<List<SearchItem>>() {}.type
        val items = gson.fromJson<List<SearchItem>>(jsonString, listPersonType)
        return items ?: listOf()
    }

    fun getSearchHistory(): LiveData<List<SearchHistory>> {
        return searchDao.getAllSearchHistory()
    }
}