package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object SearchNavigation : DynamicFeature<Intent> {

    private const val SEARCH = "com.aucto.search.ui.SearchActivity"

    override val dynamicStart: Intent?
        get() = SEARCH.loadIntentOrNull()
}
