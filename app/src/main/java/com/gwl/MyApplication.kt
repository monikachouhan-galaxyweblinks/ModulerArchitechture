package com.gwl

import androidx.multidex.MultiDexApplication
import com.gwl.core.LoginManager

// * Created on 14/1/20.
/**
 * @author GWL
 */
class MyApplication : MultiDexApplication() {

    init {
        instance = this
    }

    //region - Companion function
    companion object {
        lateinit var instance: MyApplication
        val loginManager by lazy { LoginManager.getInstance(instance) }
    }
}