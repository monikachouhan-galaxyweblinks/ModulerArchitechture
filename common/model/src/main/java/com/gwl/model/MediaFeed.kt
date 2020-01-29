package com.gwl.model

data class MediaFeed(
    override var type: MediaType? = MediaType.VIDEO,
    override var videoUrl: String? = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
) : ArticlesItem()
