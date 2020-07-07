package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object BlogNavigation : DynamicFeature<Intent> {

    private const val BLOG = "com.aucto.BlogListActivity"

    override val dynamicStart: Intent?
        get() = BLOG.loadIntentOrNull()
}
