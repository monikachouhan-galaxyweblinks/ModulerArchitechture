package com.gwl.navigation.features

import androidx.fragment.app.Fragment
import com.gwl.navigation.loadFragmentOrNull

object PlayerNavigation : DynamicFeature<Fragment> {

    const val PLAYER = "com.gwl.feeds.presentation.InstaFeedFragment"

    override val dynamicStart: Fragment?
        get() = PLAYER.loadFragmentOrNull()
}
