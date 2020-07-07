package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object FacebookLoginNavigation : DynamicFeature<Intent> {

    private const val FBLOGIN = "com.aucto.fblogin.FacebookActivity"

    override val dynamicStart: Intent?
        get() = FBLOGIN.loadIntentOrNull()
}
