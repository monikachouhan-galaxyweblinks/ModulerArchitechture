package com.gwl.home

import android.util.Log
import com.gwl.base.BottomNavigationActivity
import com.gwl.core.initViewModel
import com.gwl.home.databinding.ActivityHome1Binding
import com.gwl.navigation.features.SearchNavigation

class HomeActivity : BottomNavigationActivity<ActivityHome1Binding, HomeViewModel>() {

    override fun getViewModel(): HomeViewModel {
        return initViewModel { HomeViewModel() }
    }

    // region - Click events
    override fun onProfileSelect(fromSideMenu: Boolean) {
        Log.e(HomeActivity::class.java.simpleName, "onProfileSelect")
    }

    override fun onSearchSelect(fromSideMenu: Boolean) {
        Log.e(HomeActivity::class.java.simpleName, "onSearchSelect")
    }

    override fun onSettingSelect(fromSideMenu: Boolean) {
        Log.e(HomeActivity::class.java.simpleName, "onSettingSelect")
    }

    override fun onAboutSelect(fromSideMenu: Boolean) {
        Log.e(HomeActivity::class.java.simpleName, "onAboutSelect")
    }

    override fun onLogoutSelect(fromSideMenu: Boolean) {
        Log.e(HomeActivity::class.java.simpleName, "onLogoutSelect")
    }

    override fun onFeedSelect(fromSideMenu: Boolean) {
        Log.e(HomeActivity::class.java.simpleName, "onFeedSelect")
    }
    // endregion
}
