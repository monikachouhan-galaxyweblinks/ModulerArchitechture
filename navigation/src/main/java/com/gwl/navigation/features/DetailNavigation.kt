package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object DetailNavigation : DynamicFeature<Intent> {

    private const val LOGIN = "com.gwl.detail.DetailActivity"

    override val dynamicStart: Intent?
        get() = LOGIN.loadIntentOrNull()
}
