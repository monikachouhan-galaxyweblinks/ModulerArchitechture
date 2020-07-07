package com.blog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.gwl.model.BlogPostResponse


open class BlogAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    AutoUpdatableAdapter {

    // region - Companion object
    companion object {
        const val VIEW_TYPE_VIDEO = 1
        const val VIEW_TYPE_BLANK = 2
        const val VIEW_TYPE_HEADER = 3
    }
    // endregion

    // region - Public variable
    var blogPostResponseList: List<BlogPostResponse>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    // endregion

    // region - Override functions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val blogView = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context), R.layout.blog_item, parent, false
        )
        return BlogItemViewHolder(blogView)
    }

    override fun getItemCount(): Int {
        return blogPostResponseList?.size ?: 0
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        blogPostResponseList?.also {
            when (holder) {
                is BlogItemViewHolder -> {
                    val videoItem = it[position]
                    holder.bind(videoItem)
                }
            }
        }
    }
    // endregion
}
