package com.gwl.search.ui

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gwl.core.BaseViewModel
import com.gwl.model.SearchHistory
import com.gwl.model.SearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// * Created on 16/3/20.
/**
 * @author GWL
 */
class SearchViewModel : BaseViewModel() {

    val mAdapter = SearchAdapter()
    val searchRepository: SearchRepository by lazy { SearchRepository() }
    val data: ObservableField<List<SearchItem>> by lazy { ObservableField<List<SearchItem>>() }
    val defaultList: MutableLiveData<List<SearchItem>> by lazy { MutableLiveData<List<SearchItem>>() }
    var searchHistory: LiveData<List<SearchHistory>> = searchRepository.getSearchHistory()
    var lastSearchTerm: String = ""

    init {
        initData()
    }

    fun initData() {
        val value = searchRepository.getJsonDataFromAsset()
        defaultList.postValue(value)
        Log.d("initData", "initData ${defaultList.value?.size} ")
        data.set(value)
    }

    fun addSearchHistory(searchTerm: String) {
        GlobalScope.launch(Dispatchers.IO) {
            searchRepository.searchDao.add(SearchHistory(history = searchTerm))
            Log.d(
                "addSearchHistory", "addSearchHistory ${searchRepository.searchDao.getSearchHistories()} "
            )
        }
    }

    /* fun getSearchHistory(): List<String> {
         return searchRepository.searchDao.getSearchHistories().map { it.history }
     }*/

    fun updateData(newText: String) {
        val list = defaultList.value?.filter { it.title.contains(newText) }
        Log.d("initData", "onQueryTextChange ${defaultList.value?.size} ")
        data.set(list)
        if (list?.isNotEmpty() == true)
            lastSearchTerm = newText

    }
}