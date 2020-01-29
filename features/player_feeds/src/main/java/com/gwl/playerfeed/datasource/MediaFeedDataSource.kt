package com.gwl.playerfeed.datasource

import com.gwl.MyApplication
import com.gwl.core.datasource.PathDataSource
import com.gwl.model.Media
import com.gwl.model.ArticlesItem
import com.gwl.model.MediaType
import com.networking.result.APIResult
import kotlinx.android.parcel.Parcelize

@Parcelize
class MediaFeedDataSource : PathDataSource<ArticlesItem>(MyApplication.instance.networkAPI) {
    override suspend fun fetch(page: Int, count: Int): APIResult<List<ArticlesItem>> {
        return api.getPlayerFeeds(page, count)
            .mapArticles { it?.toMutableList()?.getList() ?: listOf() }
    }

    private fun MutableList<ArticlesItem>.getList(): List<ArticlesItem> {
        this.forEach {
            val media = getMediaMockList()
            it.type = media.type
            it.sourceUrl = media.source
        }
        return this
    }

    fun getMediaMockList(): Media {
        val list = arrayListOf(
            Media(
                MediaType.IMAGE,
                "https://cdn.pixabay.com/photo/2016/10/27/22/53/heart-1776746_960_720.jpg"
            ),
            Media(
                MediaType.IMAGE,
                "https://cdn.pixabay.com/photo/2015/03/30/20/33/heart-700141_960_720.jpg"
            ),
            Media(
                MediaType.IMAGE,
                "https://dhggywfvre0o8.cloudfront.net/app/uploads/2017/12/04150643/Typeform-Blog-Gifs-Inline02.gif"
            ),
            Media(
                MediaType.IMAGE,
                "https://wp-modula.com/wp-content/uploads/2018/12/giphy-3.gif"
            ),
            Media(
                MediaType.VIDEO,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
            ),
            Media(
                MediaType.VIDEO,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4"
            ),
            Media(
                MediaType.VIDEO,
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/SubaruOutbackOnStreetAndDirt.mp4"
            ),
            Media(MediaType.MP3, "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"),
            Media(MediaType.MP3, "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-7.mp3")
        )

        return list.random()
    }
}