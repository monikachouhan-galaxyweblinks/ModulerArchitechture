package com.gwl

import androidx.multidex.MultiDexApplication
import com.gwl.core.LoginManager
import com.networking.NetworkingApiApplication
import com.networking.client.server.NetworkAPI
import com.networking.client.server.NetworkAPIFactory

// * Created on 14/1/20.
/**
 * @author GWL
 */
class MyApplication : MultiDexApplication() {
    val networkAPI: NetworkAPI by lazy { NetworkAPIFactory.standardClient(this) }

    init {
        instance = this
    }

    //region - Companion function
    companion object {
        lateinit var instance: MyApplication
        val loginManager by lazy { LoginManager.getInstance(instance) }
    }

    override fun onCreate() {
        super.onCreate()
        NetworkingApiApplication.init(this)
    }
}