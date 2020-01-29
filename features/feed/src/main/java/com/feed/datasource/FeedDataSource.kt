package com.feed.datasource

import com.gwl.MyApplication
import com.gwl.core.datasource.PathDataSource
import com.gwl.model.ArticlesItem
import com.gwl.model.Media
import com.gwl.model.MediaType
import com.networking.result.APIResult
import kotlinx.android.parcel.Parcelize

@Parcelize
class FeedDataSource : PathDataSource<ArticlesItem>(MyApplication.instance.networkAPI) {
    override suspend fun fetch(page: Int, count: Int): APIResult<List<ArticlesItem>> {
        return api.getFeedList(page.toLong(), count)
    }
}