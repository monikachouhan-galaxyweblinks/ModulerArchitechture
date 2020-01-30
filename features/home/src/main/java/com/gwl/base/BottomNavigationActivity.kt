package com.gwl.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.gwl.core.BaseViewModel
import com.gwl.home.BuildConfig
import com.gwl.home.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

abstract class BottomNavigationActivity<B : ViewDataBinding, V : BaseViewModel> :
    DrawerActivity<B, V>() {

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        setNavigationState(BuildConfig.IS_DRAWER)
    }

    private fun setNavigationState(isEnabled: Int) {
        if (isEnabled == 1)
            bottomNavigation.visibility = View.GONE
        else if (isEnabled == 2 || isEnabled == 3) {
            if (isEnabled == 2)
                toolbar.setPadding(24, 0, 0, 0)
            bottomNavigation.visibility = View.VISIBLE
            bottomNavigation.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.actionHome -> onFeedSelect(true)

                }
                true
            }
        }
    }
}
