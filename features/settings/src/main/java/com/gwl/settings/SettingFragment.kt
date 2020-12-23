package com.gwl.settings

import android.util.Log
import com.gwl.MyApplication
import com.gwl.core.BaseFragment
import com.gwl.core.KEY_THEME_SETTING
import com.gwl.core.LoginManager
import com.gwl.core.initViewModel
import com.gwl.settings.databinding.FragmentSettingsBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
class SettingFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {
    private val loginManager: LoginManager by lazy { MyApplication.loginManager }
    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun getViewModel(): SettingsViewModel {
        return initViewModel { SettingsViewModel() }
    }

    override suspend fun initObservers() {
        super.initObservers()
        mViewModel.isAutoPlay.collectLatest {
            mViewModel.updateAutoPlaySetting(isChecked = it)
            mDataBinding.checkboxAutoPlay.isChecked = it

        }
        mViewModel.isDarkTheme.collectLatest {
            Log.d("IT_SAMPLE", "" + loginManager.getBoolean(KEY_THEME_SETTING) + "_" + it)
            mDataBinding.darkThemeSwitch.isChecked = it
            if (loginManager.getBoolean(KEY_THEME_SETTING) != it) {
                mViewModel.updateDarkThemeSetting(isChecked = it)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d("updateAutoPlaySetting", " onHiddenChanged $hidden")
        if (!hidden)
            mViewModel?.refreshSettings()
    }

    override fun removeObservers() {
        super.removeObservers()
        /*  mViewModel.isAutoPlay.removeObservers(this)
          mViewModel.isDarkTheme.removeObservers(this)*/
    }

    override fun getBindingVariable(): Int {
        return BR.viewmodel
    }
}
