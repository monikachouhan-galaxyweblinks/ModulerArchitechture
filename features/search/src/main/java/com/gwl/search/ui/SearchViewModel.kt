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
    // var searchHistory: LiveData<List<SearchHistory>> = searchRepository.getSearchHistory()
    var lastSearchTerm: String = ""
    val searchHistory: LiveData<List<SearchHistory>> get() = _searchHistory
    private var _searchHistory: MutableLiveData<List<SearchHistory>> = MutableLiveData()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            _searchHistory.postValue((searchRepository.searchDao.getSearchHistories()))
        }
        initData()
    }

    fun initData() {
        val value = searchRepository.getJsonDataFromAsset()
        defaultList.postValue(value)
        Log.e("initData", "initData ${defaultList.value?.size} ")
        data.set(value)
    }

    fun addSearchHistory(searchTerm: String) {
        GlobalScope.launch(Dispatchers.IO) {
            Log.e("addSearchHistory iii ", "${searchTerm}")
            searchRepository.searchDao.add(SearchHistory(history = searchTerm))
            Log.e(
                "addSearchHistory xxx ",
                "addSearchHistory ${searchRepository.searchDao.getSearchHistories()} "
            )
        }
    }

    /* fun getSearchHistory(): List<String> {
         return searchRepository.searchDao.getSearchHistories().map { it.history }
     }*/

    fun updateData(newText: String) {
        val list = defaultList.value?.filter { it.title.contains(newText) }
        Log.e("updateData", "onQueryTextChange ${defaultList.value?.size}   ${newText}")
        data.set(list)
        if (list?.isNotEmpty() == true)
            lastSearchTerm = newText

    }

    fun getMatchedSuggetion(text: String): List<String> {
        val allData = searchRepository.searchDao.getSearchHistories().map { it.history }
        return allData.filter { it.contains(text, true) }
    }
}