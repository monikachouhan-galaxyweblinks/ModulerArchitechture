package com.feed

import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.model.ArticlesItem

class FeedViewHolder(itemRowBind: ViewDataBinding) :
    BaseViewHolder<ArticlesItem>(itemRowBind) {
    override fun bind(
        data: ArticlesItem,
        onItemClickListener: BaseAdapter.OnItemClickListener<ArticlesItem>?
    ) {
        super.bind(data, null)
        itemRowBinding.setVariable(BR.item, data)
    }
}