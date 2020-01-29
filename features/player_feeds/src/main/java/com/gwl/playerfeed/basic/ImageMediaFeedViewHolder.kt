package com.gwl.playerfeed.basic

import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.model.MediaFeed
import com.gwl.playerfeed.BR

/**
 * @author GWL
 */
class ImageMediaFeedViewHolder(itemRowBind: ViewDataBinding) :
    BaseViewHolder<MediaFeed>(itemRowBind) {
    override fun bind(
        data: MediaFeed,
        onItemClickListener: BaseAdapter.OnItemClickListener<MediaFeed>?
    ) {
        super.bind(data, null)
        itemRowBinding.setVariable(BR.item, data)
    }
}