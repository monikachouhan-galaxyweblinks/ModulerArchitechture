package com.gwl.core

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @author GWL
 */
open class BaseViewHolder<T>(val itemRowBinding: ViewDataBinding) :
    RecyclerView.ViewHolder(itemRowBinding.root) {

    open fun bind(data: T, onItemClickListener: BaseAdapter.OnItemClickListener<T>?) {
        //TODO - set common binding variables
        //itemRowBinding.setVariable(BR.item, data)
        //itemRowBinding.setVariable(BR.clickListener, onItemClickListener);
    }
}
