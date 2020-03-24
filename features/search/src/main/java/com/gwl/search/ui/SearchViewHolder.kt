package com.gwl.search.ui

import android.util.Log
import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.model.SearchItem
import com.gwl.search.BR

class SearchViewHolder(itemRowBind: ViewDataBinding) : BaseViewHolder<SearchItem>(itemRowBind) {
    override fun bind(
        data: SearchItem,
        onItemClickListener: BaseAdapter.OnItemClickListener<SearchItem>?
    ) {
        super.bind(data, null)
        Log.d("SearchViewHolder", "SearchViewHolder $data")
        itemRowBinding.setVariable(BR.item, data)
    }
}