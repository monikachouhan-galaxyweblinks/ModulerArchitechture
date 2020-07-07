package com.aucto.navigation.features

import androidx.fragment.app.Fragment
import com.aucto.navigation.loadFragmentOrNull

object PlayerNavigation : DynamicFeature<Fragment> {

    const val PLAYER = "com.aucto.feeds.presentation.InstaFeedFragment"

    override val dynamicStart: Fragment?
        get() = PLAYER.loadFragmentOrNull()
}
