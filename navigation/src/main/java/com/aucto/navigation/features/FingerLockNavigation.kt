package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object FingerLockNavigation : DynamicFeature<Intent> {

    private const val FingerLock = "com.aucto.fingerLock.FingerPrintActivity"

    override val dynamicStart: Intent?
        get() = FingerLock.loadIntentOrNull()
}
