package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object FeedNavigation : DynamicFeature<Intent> {

    private const val FEED = "com.feed.FeedActivity"

    override val dynamicStart: Intent? get() = FEED.loadIntentOrNull()
}
