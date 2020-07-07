package com.aucto.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders


// region - Public function

fun AppCompatActivity.initializeToolbar(toolbar: Toolbar, title: String, showBackButton: Boolean) {
    setSupportActionBar(toolbar)
    supportActionBar?.title = title
    supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
    supportActionBar?.setHomeButtonEnabled(showBackButton)
}

fun Activity.navigateToNextScreen(screen: Class<*>, bundle: Bundle) {
    startActivity(Intent(this, screen).apply { putExtras(bundle) })
}

fun Activity.navigateToNextScreenWithAnimation(
    screen: Class<*>, bundle: Bundle, animationBundle: Bundle?
) {
    startActivity(Intent(this, screen).apply { putExtras(bundle) }, animationBundle)
}

inline fun <reified T : BaseViewModel> AppCompatActivity.initViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProviders.of(this).get(T::class.java)
    else
        ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
}

inline fun <reified T : BaseViewModel> Fragment.initViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProviders.of(this).get(T::class.java)
    else
        ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
}
// endregion