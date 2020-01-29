package com.gwl.detail

import com.gwl.core.BaseViewModel
import com.gwl.model.ArticlesItem
import com.gwl.model.MediaType
import com.gwl.playerfeed.ExoPlayerViewHelper

/**
 * @author GWL
 */
class DetailViewModel(val item: ArticlesItem) : BaseViewModel() {

    fun getFeedType(): MediaType? {
        return item.type
    }

    fun getVideoUrl(): String? {
        return item.videoUrl
    }

}