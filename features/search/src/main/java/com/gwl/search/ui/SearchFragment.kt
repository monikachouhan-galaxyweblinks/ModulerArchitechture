package com.gwl.search.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.lifecycle.Observer
import com.gwl.core.BaseFragment
import com.gwl.core.initViewModel
import com.gwl.search.BR
import com.gwl.search.R
import com.gwl.search.databinding.ActivitySearchBinding
import com.gwl.search.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Miguel Catalan Bañuls
 */
class SearchFragment : BaseFragment<ActivitySearchBinding, SearchViewModel>() {

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun getViewModel(): SearchViewModel {
        return initViewModel { SearchViewModel() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        toolbar?.visibility = View.GONE
        //  setToolBar(toolbar)
    }

    override suspend fun initObservers() {
        super.initObservers()
        initSearch()
        initListeners()
    }

    override fun removeObservers() {
        super.removeObservers()

    }
    private suspend fun initSearch() {
        mViewModel.searchHistory.collectLatest {
            val historyList: List<String> = it?.map { it.history } ?: listOf()
            search_view?.setHistoryORSuggestions(historyList)
        }
    }

    private fun initListeners() {
        mViewModel.searchRepository.searchDao.getAllSearchHistory().observe(viewLifecycleOwner,
            Observer {
                search_view?.setHistoryORSuggestions(it.map { it.history })
            })
        search_view?.setVoiceSearch(false)
        search_view?.setCursorDrawable(R.drawable.custom_cursor)
        search_view?.setEllipsize(true)
        search_view?.setHistoryORSuggestions(mViewModel.searchHistory.value?.map { it.history })
        search_view?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    mViewModel.addSearchHistory(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                GlobalScope.launch(Dispatchers.IO) {
                    if (newText.isNotEmpty()) {
                        val matchedList = mViewModel.getMatchedSuggestion(newText)
                        withContext(Dispatchers.Main) {
                            search_view?.setHistoryORSuggestions(matchedList)
                        }
                    }
                }
                mViewModel.updateData(newText)
                return true
            }
        })
        search_view?.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                Log.d("setOnSearchViewListener", "setOnSearchViewListener")
                mViewModel.showSearch.set(true)
            }

            override fun onSearchViewClosed() {
                mViewModel.showSearch.set(false)
                if (mViewModel.lastSearchTerm.isNotEmpty()) {
                    mViewModel.addSearchHistory(mViewModel.lastSearchTerm)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        val item = menu.findItem(R.id.action_search)
        search_view?.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDetach() {
        if (search_view?.isSearchOpen == true) {
            search_view?.closeSearch()
        }
        super.onDetach()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == Activity.RESULT_OK) {
            val matches =
                data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (matches != null && matches.size > 0) {
                val searchWrd = matches[0]
                if (!TextUtils.isEmpty(searchWrd)) {
                    search_view?.setQuery(searchWrd, false)
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}// Required empty public constructor