package com.gwl.playerfeed.basic

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gwl.model.MediaFeed
import com.gwl.playerfeed.R

// * Created on 20/1/20.
/**
 * @author GWL
 */
class MediaFeedAdapter : PagedListAdapter<MediaFeed, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<MediaFeed>() {
        override fun areItemsTheSame(oldItem: MediaFeed, newItem: MediaFeed) =
            (oldItem::class == newItem::class)

        override fun areContentsTheSame(oldItem: MediaFeed, newItem: MediaFeed) = (
                oldItem == newItem)
    }
) {
    //    var receivedFirstLoad = false
    var isAutoPlay: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_view_video, parent, false
        )
        Log.d("onCreateViewHolder","onCreateViewHolder - --- ")
        return VideoFeedViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val feedItem = getItem(position)
        feedItem?.also {
            (viewHolder as VideoFeedViewHolder).autoplay =isAutoPlay
            (viewHolder as VideoFeedViewHolder).bind(it, null)
        }
    }
}

interface ClickListener {
    fun onclick(item: String)
}