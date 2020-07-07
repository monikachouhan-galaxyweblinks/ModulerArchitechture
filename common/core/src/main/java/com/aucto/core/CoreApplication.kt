package com.aucto.core

import android.annotation.SuppressLint
import android.content.Context

// * Created on 14/1/20.
/**
 * @author GWL
 */
@SuppressLint("StaticFieldLeak")
object CoreApplication {

    lateinit var context: Context
    fun init(context: Context) {
        if (!this::context.isInitialized) {
            this.context = context
        }
    }
}