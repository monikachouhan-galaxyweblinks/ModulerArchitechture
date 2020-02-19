package com.gwl.feeds.adapter

import android.view.ViewGroup
import com.gwl.core.BasePagerAdapter
import com.gwl.feeds.BR
import com.gwl.feeds.R
import com.gwl.model.CarouselImage

class SlidingImageAdapter : BasePagerAdapter<CarouselImage>() {
    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        super.instantiateItem(view, position)
        binding?.setVariable(BR.image, list?.get(position)?.image)
        return binding?.root ?: view
    }

    override val layoutId: Int get() = R.layout.slidingimages_layout

}