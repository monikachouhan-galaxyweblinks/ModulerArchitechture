package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object LoginNavigation : DynamicFeature<Intent> {

    private const val LOGIN = "com.gwl.login.LoginActivity"

    override val dynamicStart: Intent?
        get() = LOGIN.loadIntentOrNull()
}
