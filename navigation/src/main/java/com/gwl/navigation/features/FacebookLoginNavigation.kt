package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object FacebookLoginNavigation : DynamicFeature<Intent> {

    private const val FBLOGIN = "com.gwl.fblogin.FacebookActivity"

    override val dynamicStart: Intent?
        get() = FBLOGIN.loadIntentOrNull()
}
