package com.gwl

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.gwl.home.R
import com.gwl.navigation.loadFragmentOrNull

object HomeConfiguration {
    fun getMenuItems(): List<CustomMenu> {
        return listOf<CustomMenu>(
            CustomMenu.FeedMenu(
                "com.feed.TestFragment",
                null,
                StringUtil.getString(R.string.feed),
                R.drawable.ic_home
            ),
            CustomMenu.ProfileMenu(
                "com.feed.TestFragment",
                null,
                StringUtil.getString(R.string.profile),
                R.drawable.ic_profile
            ),
            CustomMenu.SettingMenu(
                "com.feed.TestFragment",
                null,
                StringUtil.getString(R.string.setting),
                R.drawable.ic_settings
            )
        )
    }
}

sealed class CustomMenu(
    val fragment: String, val arguments: Bundle?,
    val title: String, val icon: Int
) {

    /**
     *  Method returns instance of a fragment
     *  It might be null in case of fragment class name with package identifier is not valid
     */
    fun getFragment(): Fragment? = fragment.loadFragmentOrNull()


    /**
     *  Add required menu items of type [CustomMenu]
     *
     *  For example @sample
     *  class FeedMenu(fragment: String, title: String, icon: Int) : CustomMenu(fragment, title, icon)
     */
    class FeedMenu(fragment: String, arguments: Bundle?, title: String, icon: Int) :
        CustomMenu(fragment, arguments, title, icon)

    class ProfileMenu(fragment: String, arguments: Bundle?, title: String, icon: Int) :
        CustomMenu(fragment, arguments, title, icon)

    class SettingMenu(fragment: String, arguments: Bundle?, title: String, icon: Int) :
        CustomMenu(fragment, arguments, title, icon)
}


object StringUtil {
    @JvmStatic
    fun getString(@StringRes messageId: Int): String =
        MyApplication.instance.getString(messageId)
}