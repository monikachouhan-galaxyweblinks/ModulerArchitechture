package com.gwl.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.gwl.HomeConfiguration.getMenuItems
import com.gwl.core.BaseViewModel
import com.gwl.home.BuildConfig
import com.gwl.navigation.loadFragmentOrNull
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
            setupMenuItems(false)
            if (isEnabled == 2)
                toolbar.setPadding(24, 0, 0, 0)
            bottomNavigation.visibility = View.VISIBLE
            if (cachedFragment.isEmpty()) {
                getMenuItems().forEachIndexed { index, customMenu ->
                    customMenu.fragment.loadFragmentOrNull()?.also { cachedFragment[index] = it }
                }
            }
            cachedFragment[SELECTED_ITEM]?.also {
                changeFragment(it,SELECTED_ITEM.toString())
            }
            bottomNavigation.setOnNavigationItemSelectedListener { item ->
                val fragment: Fragment? = cachedFragment[item.itemId]
                fragment?.also { changeFragment(fragment, item.itemId.toString()) }
                true
            }
        }
    }

}
