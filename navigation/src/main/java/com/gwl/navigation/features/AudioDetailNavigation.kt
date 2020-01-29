package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object AudioDetailNavigation : DynamicFeature<Intent> {

    private const val VIDEO_DETAIL = "com.gwl.details.audio.AudioDetailActivity"

    override val dynamicStart: Intent?
        get() = VIDEO_DETAIL.loadIntentOrNull()
}
