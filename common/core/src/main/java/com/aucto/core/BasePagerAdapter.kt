package com.aucto.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter

/**
 * @author GWL
 */
abstract class BasePagerAdapter<T> : PagerAdapter() {
    var list: MutableList<T>? = null
    private var clickListener: OnItemClickListener<T>? = null

    var binding: ViewDataBinding? = null
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
        if (this.list != null) this.list?.clear()
        this.list = list
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return list?.size ?: 0
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(view.context),
            layoutId, view, false
        )
        view.addView(binding?.root)
        return binding?.root ?: view
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }
}

