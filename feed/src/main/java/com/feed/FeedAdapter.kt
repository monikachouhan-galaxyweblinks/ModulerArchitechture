package com.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableBoolean
import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseAdapter
import com.gwl.core.BaseViewHolder
import com.gwl.model.ArticlesItem

// * Created on 20/1/20.
/**
 * @author GWL
 */
class FeedAdapter : BaseAdapter<ArticlesItem>()  {

    override val layoutId: Int get() = R.layout.item_row_feed

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            BaseViewHolder<ArticlesItem> {
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return FeedViewHolder(view)
    }
}

interface ClickListener {
    fun onclick(item: String)
}