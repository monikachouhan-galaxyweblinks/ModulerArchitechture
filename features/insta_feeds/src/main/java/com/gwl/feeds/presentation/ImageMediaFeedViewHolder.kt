package com.gwl.feeds.presentation

import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.model.InstaFeed
import com.gwl.feeds.BR

/**
 * @author GWL
 */
class ImageMediaFeedViewHolder(itemRowBind: ViewDataBinding) :
    BaseViewHolder<InstaFeed>(itemRowBind) {
    override fun bind(
        data: InstaFeed,
        onItemClickListener: BaseAdapter.OnItemClickListener<InstaFeed>?
    ) {
        super.bind(data, null)
        itemRowBinding.setVariable(BR.itemClick, onItemClickListener)
        itemRowBinding.setVariable(BR.item, data)
    }
}