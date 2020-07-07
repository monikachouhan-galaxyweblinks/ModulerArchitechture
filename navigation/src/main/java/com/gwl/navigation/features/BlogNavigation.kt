package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object BlogNavigation : DynamicFeature<Intent> {

    private const val BLOG = "com.blog.BlogListActivity"

    override val dynamicStart: Intent?
        get() = BLOG.loadIntentOrNull()
}
