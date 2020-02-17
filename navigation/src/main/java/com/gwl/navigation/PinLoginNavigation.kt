package com.gwl.navigation

import android.content.Intent
import com.gwl.navigation.features.DynamicFeature
import com.gwl.navigation.loadIntentOrNull

object PinLoginNavigation : DynamicFeature<Intent> {

    private const val FingerLock = "com.gwl.mpin.activity.MpinActivity"

    override val dynamicStart: Intent?
        get() = FingerLock.loadIntentOrNull()
}
