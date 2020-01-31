package com.gwl

import android.app.Application
import com.gwl.core.CoreApplication
import com.gwl.core.LoginManager
import com.networking.NetworkingApiApplication
import com.networking.client.server.NetworkAPI
import com.networking.client.server.NetworkAPIFactory

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
    }

    override fun onCreate() {
        super.onCreate()
        CoreApplication.init(this)
        NetworkingApiApplication.init(this)
    }
}