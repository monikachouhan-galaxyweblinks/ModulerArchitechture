package com.gwl.feeds.presentation

import androidx.databinding.ObservableInt
import androidx.databinding.ViewDataBinding
import com.gwl.MyApplication
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.feeds.BR
import com.gwl.model.InstaFeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author GWL
 *
 */
class ImageMediaFeedViewHolder(itemRowBind: ViewDataBinding) :
    BaseViewHolder<InstaFeed>(itemRowBind) {
    private val favDao by lazy { MyApplication.database.favDao() }
    val likeCount: ObservableInt by lazy { ObservableInt(0) }

    override fun bind(
        data: InstaFeed,
        onItemClickListener: BaseAdapter.OnItemClickListener<InstaFeed>?
    ) {
        super.bind(data, null)
        itemRowBinding.setVariable(BR.itemClick, onItemClickListener)
        val count = if (checkIsFav(data.id)) data.likes.count + 1 else data.likes.count
        data.likes.count = count
        itemRowBinding.setVariable(BR.item, data)
        itemRowBinding.setVariable(BR.likeCount, likeCount)
        itemRowBinding.setVariable(BR.position, adapterPosition)
        itemRowBinding.setVariable(BR.isFave, isFavorite)
        likeCount.set(if (checkIsFav(data.id)) data.likes.count + 1 else data.likes.count)
    }

    fun checkIsFav(id: String): Boolean {
        var isFave = false
        GlobalScope.launch(Dispatchers.IO) {
            val data = favDao.getFavById(id)
            isFave = data != null && position == data.position
            isFavorite.set(isFave)
        }

        return isFave
    }

}