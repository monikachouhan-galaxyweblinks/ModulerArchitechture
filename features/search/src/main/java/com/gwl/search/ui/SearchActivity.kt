package com.gwl.search.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.gwl.core.BaseActivity
import com.gwl.search.BR
import com.gwl.search.R
import com.gwl.search.databinding.ActivityDefaultBinding
import com.gwl.search.materialsearchview.MaterialSearchView
import com.gwl.search.materialsearchview.MaterialSearchView.SearchViewListener
import kotlinx.android.synthetic.main.activity_default.*

class SearchActivity : BaseActivity<ActivityDefaultBinding, SearchViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_default
    }

    override fun getViewModel(): SearchViewModel {
        return SearchViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBar(toolbar)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
    }

    override fun initObservers() {
        super.initObservers()
        initSearch()
        mViewModel.searchHistory.observe { it ->
            Log.d("initData", "searchHistory ${it.size} ")
            Log.d("initData", "searchHistory array ${it.map { it.history }.size} ")
            search_view?.setHistoryORSuggestions(it.map { it.history })
        }
    }

    fun initSearch() {
        search_view?.setVoiceSearch(false)
        search_view?.setCursorDrawable(R.drawable.custom_cursor)
        search_view?.setEllipsize(true)
        // Log.d("initData", "searchHistory array ${it.map { it.history }.size} ")
        search_view?.setHistoryORSuggestions(mViewModel.searchHistory.value?.map { it.history })
        // search_view?.setHistoryORSuggestions(mViewModel.getSearchHistory())
        search_view?.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (mViewModel.lastSearchTerm.isNotEmpty()) {
                    mViewModel.addSearchHistory(mViewModel.lastSearchTerm)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //Do some magic
                mViewModel.updateData(newText)
                return true
            }
        })
        search_view?.setOnSearchViewListener(object : SearchViewListener {
            override fun onSearchViewShown() {
                //Do some magic
            }

            override fun onSearchViewClosed() {
                //Do some magic
                // mViewModel.data.set(mViewModel.defaultList)
                if (mViewModel.lastSearchTerm.isNotEmpty()) {
                    mViewModel.addSearchHistory(mViewModel.lastSearchTerm)
                    Snackbar.make(
                            findViewById(R.id.container),
                            "Query: onSearchViewClosed",
                            Snackbar.LENGTH_LONG
                        )
                        .show()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu.findItem(R.id.action_search)
        search_view?.setMenuItem(item)
        return true
    }

    override fun onBackPressed() {
        if (search_view?.isSearchOpen == true) {
            search_view?.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
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
}