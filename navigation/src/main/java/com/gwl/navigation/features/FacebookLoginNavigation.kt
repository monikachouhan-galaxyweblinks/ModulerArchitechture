package com.gwl.navigation.features

import android.content.Intent
import com.gwl.navigation.loadIntentOrNull

object FacebookLoginNavigation : DynamicFeature<Intent> {

    private const val LOGIN = "com.gwl.facebook_login.FacebookLoginActivity"

    override val dynamicStart: Intent?
        get() = LOGIN.loadIntentOrNull()
}
