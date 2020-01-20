package com.gwl.core

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders


// region - Public function
/*
fun getContext(): Context {
    return MyApplication.instance
}
*/

fun AppCompatActivity.initializeToolbar(toolbar: Toolbar, title: String, showBackButton: Boolean) {
    setSupportActionBar(toolbar)
    supportActionBar?.title = title
    supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)
    supportActionBar?.setHomeButtonEnabled(showBackButton)
}

inline fun <reified T : BaseViewModel> AppCompatActivity.initViewModel(noinline creator: (() -> T)? = null): T {
    return if (creator == null)
        ViewModelProviders.of(this).get(T::class.java)
    else
        ViewModelProviders.of(this, BaseViewModelFactory(creator)).get(T::class.java)
}
// endregion