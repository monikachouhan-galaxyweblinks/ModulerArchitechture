package com.gwl.feeds.presentation

// * Created on 5/2/20.
/**
 * @author GWL
 */

import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.ViewPager
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.feeds.BR
import com.gwl.feeds.R
import com.gwl.feeds.adapter.CirclePageIndicator
import com.gwl.feeds.adapter.SlidingImageAdapter
import com.gwl.model.InstaFeed

class CarouselViewHolder(itemRowBind: ViewDataBinding) : BaseViewHolder<InstaFeed>(itemRowBind) {
    val pager = itemView.findViewById<ViewPager>(R.id.pager)
    override fun bind(
        data: InstaFeed,
        onItemClickListener: BaseAdapter.OnItemClickListener<InstaFeed>?
    ) {
        itemRowBinding.setVariable(BR.itemClick, onItemClickListener)
        val adapter = SlidingImageAdapter()
        adapter.setData(data.carosel?.toMutableList())
        pager.adapter = adapter
        itemRowBinding.setVariable(BR.item, data)
        val indicator = itemView.findViewById<CirclePageIndicator>(R.id.indicator)
        indicator.setViewPager(pager)

        //  videoUri = Uri.parse(data.sourceUrl)
    }
}