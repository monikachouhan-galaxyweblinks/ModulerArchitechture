package com.gwl.playerfeed.datasource

import com.gwl.MyApplication
import com.gwl.core.datasource.PathDataSource
import com.gwl.model.ArticlesItem
import com.networking.result.APIResult
import kotlinx.android.parcel.Parcelize

@Parcelize
class MediaFeedDataSource : PathDataSource<ArticlesItem>(MyApplication.instance.networkAPI) {
    override suspend fun fetch(page: Int, count: Int): APIResult<List<ArticlesItem>> {
        return api.getPlayerFeeds(page, count)
    }
}