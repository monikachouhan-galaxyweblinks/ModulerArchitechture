package com.gwl.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.gwl.HomeConfiguration.getMenuItems
import com.gwl.MyApplication
import com.gwl.core.BaseActivity
import com.gwl.core.BaseViewModel
import com.gwl.home.BuildConfig
import com.gwl.home.R
import com.gwl.home.databinding.NavHeaderMainBinding
import com.gwl.navigation.loadFragmentOrNull
import kotlinx.android.synthetic.main.activity_home_1.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

abstract class DrawerActivity<B : ViewDataBinding, V : BaseViewModel> : BaseActivity<B, V>(),
    NavigationView.OnNavigationItemSelectedListener {

    val cachedFragment: HashMap<Int, Fragment?> = hashMapOf()
    val SELECTED_ITEM = 0
    abstract fun onFeedSelect(fromSideMenu: Boolean)
    abstract fun onProfileSelect(fromSideMenu: Boolean)
    abstract fun onSettingSelect(fromSideMenu: Boolean)
    abstract fun onAboutSelect(fromSideMenu: Boolean)
    abstract fun onLogoutSelect(fromSideMenu: Boolean)

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment? = cachedFragment[item.itemId]
        fragment?.also { changeFragment(fragment, item.itemId.toString()) }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_home_1
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        initializeDrawer()
        if (cachedFragment.isEmpty()) {
            getMenuItems().forEachIndexed { index, customMenu ->
                customMenu.fragment.loadFragmentOrNull()?.also { cachedFragment[index] = it }
            }
        }
    }


    open fun changeFragment(fragment: Fragment?, tagFragmentName: String?) {
        val mFragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = mFragmentManager.beginTransaction()
        val currentFragment: Fragment? = mFragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }
        var fragmentTemp: Fragment? =
            mFragmentManager.findFragmentByTag(tagFragmentName)
        if (fragmentTemp == null) {
            fragmentTemp = fragment
            fragmentTemp?.also { fragmentTransaction.add(R.id.container, it, tagFragmentName) }
        } else {
            fragmentTransaction.show(fragmentTemp)
        }

        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp)
        fragmentTransaction.setReorderingAllowed(false)
        fragmentTransaction.commitNowAllowingStateLoss()
    }

    private fun initializeDrawer() {
        val navView: NavigationView = findViewById(R.id.nav_view)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(com.gwl.R.string.title_home)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, com.gwl.R.string.title_home, com.gwl.R.string.title_home
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setCheckedItem(SELECTED_ITEM)
        cachedFragment[SELECTED_ITEM]?.also {
            changeFragment(it, SELECTED_ITEM.toString())
        }
        navView.setNavigationItemSelectedListener(this@DrawerActivity)

        val viewHeader = nav_view.getHeaderView(0)
        val navViewHeaderBinding: NavHeaderMainBinding = NavHeaderMainBinding.bind(viewHeader)
        navViewHeaderBinding.user = MyApplication.loginManager.getUser()

        setDrawerState(BuildConfig.IS_DRAWER == 2, toggle)
    }

    private fun setDrawerState(isEnabled: Boolean, drawerToggle: ActionBarDrawerToggle) {
        if (isEnabled) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            drawerToggle.isDrawerIndicatorEnabled = false
            drawerToggle.syncState()
            nav_view.visibility = View.GONE
        }
        nav_view.menu.clear()
        setupMenuItems(true)
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        fragment.arguments = Bundle().apply {
            putString("tag", tag)
        }
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    fun setupMenuItems(isDrawer: Boolean) {
        bottomNavigation.menu.clear()
        getMenuItems().forEachIndexed { index, item ->
            if (item.getFragment() != null) {
                if (isDrawer)
                    nav_view.menu.add(Menu.NONE, index, Menu.NONE, item.title)
                        .setIcon(item.icon)
                else if (bottomNavigation.menu.size() < 5) //Only 5 menu item can be added
                    bottomNavigation.menu.add(Menu.NONE, index, Menu.NONE, item.title)
                        .setIcon(item.icon)
            }
        }
    }
}
