package com.gwl.feeds.datasource

import com.gwl.MyApplication
import com.gwl.core.datasource.PathDataSource
import com.gwl.model.InstaFeed
import com.networking.result.APIResult
import kotlinx.android.parcel.Parcelize

@Parcelize
class MediaFeedDataSource : PathDataSource<InstaFeed>(MyApplication.instance.networkAPI) {
    override suspend fun fetch(page: Int, count: Int): APIResult<List<InstaFeed>> {
        return api.getInstaFeeds()
    }
}