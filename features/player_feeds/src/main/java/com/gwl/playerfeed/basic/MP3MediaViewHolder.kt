package com.gwl.playerfeed.basic

import android.net.Uri
import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.model.MediaFeed
import com.gwl.playerfeed.BR
import com.gwl.playerfeed.R

// * Created on 29/1/20.
/**
 * @author GWL
 */
class MP3MediaViewHolder(itemRowBind: ViewDataBinding) : VideoFeedViewHolder(itemRowBind) {
    override var autoplay: Boolean = false


    override fun bind(
        data: MediaFeed,
        onItemClickListener: BaseAdapter.OnItemClickListener<MediaFeed>?
    ) {
        itemRowBinding.setVariable(BR.item, data)
        artWork.setImageResource(R.drawable.music_bg_thumbnail)
        videoUri = Uri.parse(data.sourceUrl)
    }
}