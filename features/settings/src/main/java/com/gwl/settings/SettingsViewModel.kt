package com.gwl.settings

import androidx.appcompat.app.AppCompatDelegate
import com.gwl.MyApplication
import com.gwl.core.BaseViewModel
import com.gwl.core.KEY_AUTO_PLAY_SETTING
import com.gwl.core.KEY_THEME_SETTING
import com.gwl.core.LoginManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author GWL
 */
@ExperimentalCoroutinesApi
class SettingsViewModel : BaseViewModel() {
    private val loginManager: LoginManager by lazy { MyApplication.loginManager }
    val isAutoPlay: MutableStateFlow<Boolean> by lazy { MutableStateFlow<Boolean>(false) }
    val isDarkTheme: MutableStateFlow<Boolean> by lazy { MutableStateFlow<Boolean>(false) }

    init {
        refreshSettings()
    }

    fun refreshSettings() {
        isAutoPlay.value = loginManager.getBoolean(KEY_AUTO_PLAY_SETTING)
        isDarkTheme.value = loginManager.getBoolean(KEY_THEME_SETTING)
    }

    fun updateAutoPlaySetting(isChecked: Boolean) {
        loginManager.setBoolean(KEY_AUTO_PLAY_SETTING, isChecked)
        isAutoPlay.value = isChecked
    }

    fun updateDarkThemeSetting(isChecked: Boolean) {
        if (isChecked)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        loginManager.setBoolean(KEY_THEME_SETTING, isChecked)
        isDarkTheme.value = isChecked
    }
}