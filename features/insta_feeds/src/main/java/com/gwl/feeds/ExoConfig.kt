package com.gwl.feeds

import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.gwl.MyApplication
import com.gwl.playerfeed.ToroExo
import com.gwl.playerfeed.Config
import com.gwl.playerfeed.MediaSourceBuilder
import java.io.File

// * Created on 30/1/20.
/**
 * @author GWL
 */
object ExoConfig {
    private var cacheFile = 2 * 1024 * 1024.toLong() // size of each cache file.
    private val cache = SimpleCache(
        File(MyApplication.instance.filesDir.path + "/toro_cache"),
        LeastRecentlyUsedCacheEvictor(cacheFile)
    )
    val config = Config.Builder()
        .setMediaSourceBuilder(MediaSourceBuilder.LOOPING)
        .setCache(cache)
        .build()

    val exoCreator = ToroExo.with(MyApplication.instance).getCreator(config)
}