package com.aucto.model

//@Parcelize
//data class MediaFeed(
//    var type: MediaType? = MediaType.VIDEO,
//    var sourceUrl: String? = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
//) : ArticlesItem(), Parcelable

class Media(
    var type: MediaType?,
    var source: String?
)
