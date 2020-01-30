package com.gwl.navigation

import androidx.fragment.app.Fragment

fun String.loadFragmentOrNull(): Fragment? =
    try {
        this.loadClassOrNull<Fragment>()?.newInstance()
    } catch (e: ClassNotFoundException) {
        null
    }
