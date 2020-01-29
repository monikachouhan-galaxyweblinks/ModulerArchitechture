package com.gwl.playerfeed.basic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gwl.core.BaseViewHolder
import com.gwl.model.MediaFeed
import com.gwl.model.MediaType
import com.gwl.playerfeed.R

// * Created on 20/1/20.
/**
 * @author GWL
 */
class MediaFeedAdapter : PagedListAdapter<MediaFeed, BaseViewHolder<MediaFeed>>(MediaCallback) {

    companion object {
        val VIDEO_VIEW_TYPE = 0
        val IMAGE_VIEW_TYPE = 1
        val MP3_VIEW_TYPE = 2
        val UNKNOWN_VIEW_TYPE = -1
    }

    //    var receivedFirstLoad = false
    var isAutoPlay: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<MediaFeed> {
        val videoView = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_view_video, parent, false
        )

        return when (viewType) {
            VIDEO_VIEW_TYPE -> VideoFeedViewHolder(videoView)
            MP3_VIEW_TYPE -> MP3MediaViewHolder(videoView)
            else -> ImageMediaFeedViewHolder(videoView)
        }
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder<MediaFeed>, position: Int) {
        val feedItem = getItem(position)
        feedItem?.also {
            viewHolder.bind(it, null)
            when (getItemViewType(position)) {
                VIDEO_VIEW_TYPE -> {
                    (viewHolder as VideoFeedViewHolder).autoplay =
                        if (feedItem.type == MediaType.VIDEO) isAutoPlay else false
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)?.type) {
            MediaType.VIDEO -> VIDEO_VIEW_TYPE
            MediaType.IMAGE -> IMAGE_VIEW_TYPE
            MediaType.MP3 -> MP3_VIEW_TYPE
            else -> UNKNOWN_VIEW_TYPE
        }
    }

    object MediaCallback : DiffUtil.ItemCallback<MediaFeed>() {
        override fun areItemsTheSame(oldItem: MediaFeed, newItem: MediaFeed) =
            (oldItem::class == newItem::class)

        override fun areContentsTheSame(oldItem: MediaFeed, newItem: MediaFeed) = (
                oldItem == newItem)
    }
}

interface ClickListener {
    fun onclick(item: String)
}