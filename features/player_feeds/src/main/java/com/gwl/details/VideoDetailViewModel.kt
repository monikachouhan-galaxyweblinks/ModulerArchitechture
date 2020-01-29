package com.gwl.details

import com.gwl.core.BaseViewModel
import com.gwl.model.ArticlesItem
import com.gwl.model.MediaType

/**
 * @author GWL
 */
class VideoDetailViewModel(val item: ArticlesItem) : BaseViewModel() {

    fun getFeedType(): MediaType? {
        return item.type
    }

    fun getVideoUrl(): String? {
        return item.sourceUrl
    }

}