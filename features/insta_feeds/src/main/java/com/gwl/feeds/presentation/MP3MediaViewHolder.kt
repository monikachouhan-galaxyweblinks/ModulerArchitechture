package com.gwl.feeds.presentation

import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.feeds.BR
import com.gwl.model.InstaFeed

// * Created on 29/1/20.
/**
 * @author GWL
 */
class MP3MediaViewHolder(itemRowBind: ViewDataBinding) : VideoFeedViewHolder(itemRowBind) {
    override var autoplay: Boolean = false


    override fun bind(
        data: InstaFeed,
        onItemClickListener: BaseAdapter.OnItemClickListener<InstaFeed>?
    ) {
        itemRowBinding.setVariable(BR.itemClick, onItemClickListener)
        isPlaying.set(false)
        itemRowBinding.setVariable(BR.item, data)
        itemRowBinding.setVariable(BR.isPlaying, isPlaying)
        //  videoUri = Uri.parse(data.sourceUrl)
    }
}