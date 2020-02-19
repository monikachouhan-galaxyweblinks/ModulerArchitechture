package com.gwl.feeds.presentation

/**
 * @author GWL
 */

import android.util.Log
import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.ViewPager
import com.gwl.MyApplication
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.feeds.BR
import com.gwl.feeds.R
import com.gwl.feeds.adapter.CirclePageIndicator
import com.gwl.feeds.adapter.SlidingImageAdapter
import com.gwl.model.InstaFeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CarouselViewHolder(itemRowBind: ViewDataBinding) : BaseViewHolder<InstaFeed>(itemRowBind) {
    val pager = itemView.findViewById<ViewPager>(R.id.pager)
    private val favDao by lazy { MyApplication.database.favDao() }
    val likeCount: ObservableInt by lazy { ObservableInt(0) }

    override fun bind(
        data: InstaFeed, onItemClickListener: BaseAdapter.OnItemClickListener<InstaFeed>?
    ) {
        itemRowBinding.setVariable(BR.itemClick, onItemClickListener)
        val adapter = SlidingImageAdapter()
        adapter.setData(data.carosel?.toMutableList())
        pager.adapter = adapter
        val count = if (checkIsFav(data.id)) data.likes.count + 1 else data.likes.count
        data.likes.count = count
        itemRowBinding.setVariable(BR.item, data)
        itemRowBinding.setVariable(BR.position, adapterPosition)
        itemRowBinding.setVariable(BR.isFave, isFavorite)
        val indicator = itemView.findViewById<CirclePageIndicator>(R.id.indicator)

        indicator.setViewPager(pager)
        Log.d("checkIsFav", "CarouselViewHolder onViewClicked click $count ${likeCount.get()} ")
    }

    fun checkIsFav(id: String): Boolean {
        var isFave = false
        GlobalScope.launch(Dispatchers.IO) {
            val data = favDao.getFavById(id)
            isFave = data != null && adapterPosition == data.position
            isFavorite.set(isFave)
        }
        return isFave
    }

}