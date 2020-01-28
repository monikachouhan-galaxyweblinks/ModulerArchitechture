package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object PlayerNavigation : DynamicFeature<Intent> {

    private const val PLAYER = "com.gwl.playerfeed.basic.BasicListActivity"

    override val dynamicStart: Intent?
        get() = PLAYER.loadIntentOrNull()
}
