package com.gwl.settings

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import com.gwl.MyApplication
import com.gwl.core.BaseViewModel
import com.gwl.core.LoginManager

/**
 * @author GWL
 */
class SettingsViewModel : BaseViewModel() {
    private val loginManager: LoginManager by lazy { MyApplication.loginManager }
    val isAutoPlay: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val isDarkTheme: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    init {
        isAutoPlay.postValue(loginManager.getBoolean(KEY_AUTO_PLAY_SETTING))
        isDarkTheme.postValue(loginManager.getBoolean(KEY_THEME_SETTING))
    }

    fun updateAutoPlaySetting(isChecked: Boolean) {
        loginManager.setBoolean(KEY_AUTO_PLAY_SETTING, isChecked)
        isAutoPlay.postValue(isChecked)
    }

    fun updateDarkThemeSetting(isChecked: Boolean) {
        if (isChecked)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        loginManager.setBoolean(KEY_THEME_SETTING, isChecked)
        isDarkTheme.postValue(isChecked)
    }
}