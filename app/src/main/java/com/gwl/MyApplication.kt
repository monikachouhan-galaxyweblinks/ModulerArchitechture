package com.gwl

import android.app.Application
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.gwl.core.LoginManager
import com.networking.NetworkingApiApplication
import com.networking.client.server.NetworkAPI
import com.networking.client.server.NetworkAPIFactory
import com.gwl.playerfeed.Config
import com.gwl.playerfeed.ExoCreator
import com.gwl.playerfeed.MediaSourceBuilder
import com.gwl.playerfeed.ToroExo
import java.io.File

// * Created on 14/1/20.
/**
 * @author GWL
 */
class MyApplication : Application() {
    val networkAPI: NetworkAPI by lazy { NetworkAPIFactory.standardClient(this) }

    init {
        instance = this
    }

    //region - Companion function
    companion object {
        lateinit var instance: MyApplication
        val loginManager by lazy { LoginManager.getInstance(instance) }
        var cacheFile = 2 * 1024 * 1024.toLong() // size of each cache file.
        var config: Config? = null
        var exoCreator: ExoCreator? = null
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level >= Application.TRIM_MEMORY_BACKGROUND) ToroExo.with(this).cleanUp()
    }
    override fun onCreate() {
        super.onCreate()
        val cache = SimpleCache(
            File(filesDir.path + "/toro_cache"),
            LeastRecentlyUsedCacheEvictor(cacheFile)
        )
        config = Config.Builder()
            .setMediaSourceBuilder(MediaSourceBuilder.LOOPING)
            .setCache(cache)
            .build()
        exoCreator = ToroExo.with(this).getCreator(config)
        NetworkingApiApplication.init(this)
    }
}