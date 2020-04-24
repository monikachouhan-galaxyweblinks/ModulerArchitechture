package com.gwl.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.model.SearchItem
import com.gwl.search.R

// * Created on 20/1/20.
/**
 * @author GWL
 */
class SearchAdapter : BaseAdapter<SearchItem>()  {

    override val layoutId: Int get() = R.layout.item_row_search

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<SearchItem> {
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), layoutId, parent, false
        )
        return SearchViewHolder(view)
    }
}

interface ClickListener {
    fun onclick(item: String)
}