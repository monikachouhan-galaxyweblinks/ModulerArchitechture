package com.aucto.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @author GWL
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<BaseViewHolder<T>>() {
    internal var list: MutableList<T>? = null
    private var clickListener: OnItemClickListener<T>? = null

    /**
     * Override for set view
     */
    @get:LayoutRes
    abstract val layoutId: Int

    fun setClickListener(clickListener: OnItemClickListener<T>) {
        this.clickListener = clickListener
    }

    /**
     * Call setData() to set dataset.
     */
    fun setData(list: MutableList<T>?) {
        //if (this.list != null) this.list?.clear()
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        list?.also {
            holder.bind(it[position], clickListener)
        }
    }

    override fun getItemCount(): Int {
        return if (list != null) list!!.size else 0
    }

    override fun getItemViewType(position: Int): Int = position


    interface OnItemClickListener<T> {
        fun onItemClick(item: T, view: View)
        fun onViewClicked(view: View, item: T, position: Int)
    }
}

