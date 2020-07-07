package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object HomeNavigation : DynamicFeature<Intent> {

    private const val HOME = "com.aucto.home.HomeActivity"

    override val dynamicStart: Intent?
        get() = HOME.loadIntentOrNull()
}
