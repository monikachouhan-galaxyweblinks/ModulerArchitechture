package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object SearchNavigation : DynamicFeature<Intent> {

    private const val SEARCH = "com.gwl.search.ui.SearchActivity"

    override val dynamicStart: Intent?
        get() = SEARCH.loadIntentOrNull()
}
