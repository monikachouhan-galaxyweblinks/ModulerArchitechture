package com.blog

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.gwl.model.BlogPostResponse

class BlogItemViewHolder(private val dataBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(dataBinding.root) {

    // region - Public function
    fun bind(item: BlogPostResponse) {
        Log.e("itemmm ","${item.body} ${item.title}")
        dataBinding.setVariable(BR.item, item)
        dataBinding.executePendingBindings()
    }
    // endregion
}