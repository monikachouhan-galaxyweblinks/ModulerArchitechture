package com.gwl.settings

import com.gwl.core.BaseFragment
import com.gwl.core.initViewModel
import com.gwl.settings.databinding.FragmentSettingsBinding

const val KEY_AUTO_PLAY_SETTING = "autoplay"

class SettingFragment : BaseFragment<FragmentSettingsBinding, SettingsViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun getViewModel(): SettingsViewModel {
        return initViewModel { SettingsViewModel() }
    }

    override fun initObservers() {
        super.initObservers()
        mViewModel.isAutoPlay.observe {
            mViewModel.updateAutoPlaySetting(isChecked = it)
        }
    }

    override fun getBindingVariable(): Int {
        return BR.viewmodel
    }
}
