package com.aucto.navigation

import android.content.Intent
import com.aucto.navigation.Navigation.PACKAGE_NAME

object Navigation {
    var PACKAGE_NAME: String = ""
}

private fun intentTo(className: String): Intent =
    if (PACKAGE_NAME.isNotEmpty()) Intent(Intent.ACTION_VIEW).setClassName(
        PACKAGE_NAME, className
    ) else
        throw IllegalArgumentException("Navigation.PACKAGE_NAME must be initialized in Application class.")

internal fun String.loadIntentOrNull(): Intent? =
    try {
        Class.forName(this).run { intentTo(this@loadIntentOrNull) }
    } catch (e: ClassNotFoundException) {
        null
    }
