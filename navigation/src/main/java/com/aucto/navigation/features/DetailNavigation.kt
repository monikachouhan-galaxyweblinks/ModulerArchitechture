package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object DetailNavigation : DynamicFeature<Intent> {

    private const val VIDEO_DETAIL = "com.aucto.details.VideoDetailActivity"

    override val dynamicStart: Intent?
        get() = VIDEO_DETAIL.loadIntentOrNull()
}
