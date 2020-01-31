package com.gwl

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.gwl.home.R
import com.gwl.navigation.features.FragmentNavigation
import com.gwl.navigation.loadFragmentOrNull

object HomeConfiguration {
    fun getMenuItems(): List<CustomMenu> {
        return listOf(
            CustomMenu(
                FragmentNavigation.PLAYER_FEED,
                null,
                StringUtil.getString(R.string.feed),
                R.drawable.ic_home
            ),
            CustomMenu(
                FragmentNavigation.PROFILE,
                null,
                StringUtil.getString(R.string.profile),
                R.drawable.ic_profile
            ),
            CustomMenu(
                "com.kijiji.TestFragment",
                null,
                StringUtil.getString(R.string.setting),
                R.drawable.ic_settings
            )
        )
    }
}

data class CustomMenu(
    val fragment: String, val arguments: Bundle?,
    val title: String, val icon: Int
) {

    /**
     *  Method returns instance of a fragment
     *  It might be null in case of fragment class name with package identifier is not valid
     */
    fun getFragment(): Fragment? = fragment.loadFragmentOrNull()
}


object StringUtil {
    @JvmStatic
    fun getString(@StringRes messageId: Int): String =
        MyApplication.instance.getString(messageId)
}