package com.gwl.playerfeed.datasource

import com.gwl.MyApplication
import com.gwl.core.datasource.PathDataSource
import com.gwl.model.MediaFeed
import com.networking.result.APIResult
import kotlinx.android.parcel.Parcelize

@Parcelize
class MediaFeedDataSource : PathDataSource<MediaFeed>(MyApplication.instance.networkAPI) {
    override suspend fun fetch(page: Int, count: Int): APIResult<List<MediaFeed>> {
        return api.getPlayerFeeds(page, count)
    }
}