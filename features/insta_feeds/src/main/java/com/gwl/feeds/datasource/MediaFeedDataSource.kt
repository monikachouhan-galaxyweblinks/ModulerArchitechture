package com.gwl.feeds.datasource

import com.gwl.MyApplication
import com.gwl.core.KEY_ACCESS_TOKEN
import com.gwl.core.datasource.PathDataSource
import com.gwl.model.InstaFeed
import com.gwl.networking.result.APIResult
import kotlinx.android.parcel.Parcelize

@Parcelize
class MediaFeedDataSource : PathDataSource<InstaFeed>(MyApplication.instance.networkAPI) {
    val token: String = MyApplication.loginManager.getString(KEY_ACCESS_TOKEN) ?: ""
    override suspend fun fetch(page: Int, count: Int): APIResult<List<InstaFeed>> {
        return api.getInstaFeeds(token)
    }
}