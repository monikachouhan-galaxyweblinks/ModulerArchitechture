package com.aucto.navigation

import android.util.Log
import androidx.fragment.app.Fragment

fun String.loadFragmentOrNull(): Fragment? =
    try {
        val frag = this.loadClassOrNull<Fragment>()?.newInstance()
        frag?.also {
            Log.d("loadFragmentOrNull","loadFragmentOrNull called ${frag::class.java.simpleName}" )
        }
        frag
    } catch (e: ClassNotFoundException) {
        null
    }
