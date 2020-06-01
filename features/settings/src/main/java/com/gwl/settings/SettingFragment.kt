package com.gwl.settings

import android.util.Log
import com.gwl.MyApplication
import com.gwl.MyApplication.Companion.loginManager
import com.gwl.core.BaseFragment
import com.gwl.core.LoginManager
import com.gwl.core.initViewModel
import com.gwl.settings.databinding.FragmentSettingsBinding

const val KEY_AUTO_PLAY_SETTING = "autoplay"
const val KEY_THEME_SETTING = "darktheme"

class SettingFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {
    private val loginManager: LoginManager by lazy { MyApplication.loginManager }
    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun getViewModel(): SettingsViewModel {
        return initViewModel { SettingsViewModel() }
    }

    override fun initObservers() {
        super.initObservers()
        mViewModel.isAutoPlay.observe {
            mViewModel.updateAutoPlaySetting(isChecked = it)
        }
        mViewModel.isDarkTheme.observe {
            Log.d("IT_SAMPLE", "" + loginManager.getBoolean(KEY_THEME_SETTING) + "_" + it)
            if (loginManager.getBoolean(KEY_THEME_SETTING) != it) {
                mViewModel.updateDarkThemeSetting(isChecked = it)
            }
        }
    }

    override fun getBindingVariable(): Int {
        return BR.viewmodel
    }
}
