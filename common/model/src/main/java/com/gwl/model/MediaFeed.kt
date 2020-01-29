package com.gwl.model

data class MediaFeed(
    override var type: MediaType? = MediaType.VIDEO,
    override var sourceUrl: String? = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
) : ArticlesItem()

class Media(var type: MediaType?, var source: String?)
