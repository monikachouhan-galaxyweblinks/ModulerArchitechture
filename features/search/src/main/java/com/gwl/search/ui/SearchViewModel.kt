package com.gwl.search.ui

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.gwl.core.BaseViewModel
import com.gwl.model.SearchHistory
import com.gwl.model.SearchItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// * Created on 16/3/20.
/**
 * @author GWL
 */
@ExperimentalCoroutinesApi
class SearchViewModel : BaseViewModel() {

    val mAdapter = SearchAdapter()
    val searchRepository: SearchRepository by lazy { SearchRepository() }
    val showSearch: ObservableBoolean by lazy { ObservableBoolean() }
    val data: ObservableField<List<SearchItem>> by lazy { ObservableField<List<SearchItem>>() }
    val defaultList: MutableStateFlow<List<SearchItem>> by lazy { MutableStateFlow<List<SearchItem>>(listOf()) }
    var lastSearchTerm: String = ""
    val searchHistory: MutableStateFlow<List<SearchHistory>> get() = _searchHistory
    private var _searchHistory: MutableStateFlow<List<SearchHistory>> = MutableStateFlow(listOf())

    init {
        GlobalScope.launch(Dispatchers.IO) {
            _searchHistory.value=searchRepository.searchDao.getSearchHistories()
        }
        initData()
    }

    private fun initData() {
        val value = searchRepository.getJsonDataFromAsset()
        defaultList.value = value
        data.set(value)
    }

    fun addSearchHistory(searchTerm: String) {
        GlobalScope.launch(Dispatchers.IO) {
            searchRepository.searchDao.add(SearchHistory(history = searchTerm))
        }
    }

    fun updateData(newText: String) {
        val list = defaultList.value?.filter { it.title.contains(newText) }
        data.set(list)
        if (list?.isNotEmpty() == true)
            lastSearchTerm = newText

    }

    fun getMatchedSuggestion(text: String): List<String> {
        val allData = searchRepository.searchDao.getAllSearchHistories(text).map { it.history }
        return allData.filter { it.contains(text, true) }
    }
}