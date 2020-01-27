package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object GoogleLoginNavigation : DynamicFeature<Intent> {

    private const val LOGIN = "com.google_login.GoogleLoginActivity"

    override val dynamicStart: Intent?
        get() = LOGIN.loadIntentOrNull()
}
