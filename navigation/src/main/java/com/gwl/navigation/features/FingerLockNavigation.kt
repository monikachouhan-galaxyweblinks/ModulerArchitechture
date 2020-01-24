package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object FingerLockNavigation : DynamicFeature<Intent> {

    private const val FingerLock = "com.fingerLock.FingerprintActivity"

    override val dynamicStart: Intent?
        get() = FingerLock.loadIntentOrNull()
}
