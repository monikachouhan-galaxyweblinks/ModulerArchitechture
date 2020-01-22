package com.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gwl.model.ArticlesItem

// * Created on 21/1/20.
/**
 * @author GWL
 */
class FeedsAdapter : PagedListAdapter<ArticlesItem, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<ArticlesItem>() {
        override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem) =
            (oldItem::class == newItem::class)

        override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem) = (
                oldItem == newItem)
    }
) {
//    var receivedFirstLoad = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.item_row_feed, parent, false
        )
        return FeedViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val feedItem = getItem(position)
        feedItem?.also {
            (viewHolder as FeedViewHolder).bind(it, null)
        }
    }
    
    override fun getItem(position: Int): ArticlesItem? {
        return super.getItem(position)
    }
}