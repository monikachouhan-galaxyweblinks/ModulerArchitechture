package com.aucto.navigation.features

import android.content.Intent
import com.aucto.navigation.loadIntentOrNull

object LoginNavigation : DynamicFeature<Intent> {

    private const val LOGIN = "com.aucto.login.LoginActivity"

    override val dynamicStart: Intent?
        get() = LOGIN.loadIntentOrNull()
}
