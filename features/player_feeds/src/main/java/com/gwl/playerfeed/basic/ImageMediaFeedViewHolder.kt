package com.gwl.playerfeed.basic

import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.model.ArticlesItem
import com.gwl.playerfeed.BR

/**
 * @author GWL
 */
class ImageMediaFeedViewHolder(itemRowBind: ViewDataBinding) :
    BaseViewHolder<ArticlesItem>(itemRowBind) {
    override fun bind(
        data: ArticlesItem,
        onItemClickListener: BaseAdapter.OnItemClickListener<ArticlesItem>?
    ) {
        super.bind(data, null)
        itemRowBinding.setVariable(BR.itemClick, onItemClickListener)
        itemRowBinding.setVariable(BR.item, data)
    }
}