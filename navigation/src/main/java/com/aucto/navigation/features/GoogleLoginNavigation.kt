package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object GoogleLoginNavigation : DynamicFeature<Intent> {

    private const val LOGIN = "com.aucto.googlelogin.GoogleLoginActivity"

    override val dynamicStart: Intent?
        get() = LOGIN.loadIntentOrNull()
}
