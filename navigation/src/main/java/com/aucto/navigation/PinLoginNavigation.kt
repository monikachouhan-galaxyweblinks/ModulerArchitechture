package com.aucto.navigation

import android.content.Intent
import com.aucto.navigation.features.DynamicFeature

object PinLoginNavigation : DynamicFeature<Intent> {

    private const val FingerLock = "com.aucto.mpin.activity.MpinActivity"

    override val dynamicStart: Intent?
        get() = FingerLock.loadIntentOrNull()
}
