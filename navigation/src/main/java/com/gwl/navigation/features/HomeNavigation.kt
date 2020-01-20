package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object HomeNavigation : DynamicFeature<Intent> {

    private const val HOME = "com.gwl.home.HomeActivity"

    override val dynamicStart: Intent?
        get() = HOME.loadIntentOrNull()
}
