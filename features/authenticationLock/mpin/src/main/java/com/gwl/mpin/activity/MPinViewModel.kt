package com.gwl.mpin.activity

import androidx.databinding.ObservableField
import com.gwl.core.BaseViewModel
import com.gwl.core.StringUtil
import com.gwl.mpinlock.fragments.PFLockScreenFragment
import com.mpin.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * @author GWL
 */
@ExperimentalCoroutinesApi
class MPinViewModel : BaseViewModel(), PFLockScreenFragment.OnPFLockScreenLoginListener {

    private var _showToast: MutableStateFlow<String> = MutableStateFlow("")
    private var _navigateToHome: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private var successMessage = ObservableField("")
    val navigateToHome: StateFlow<Boolean> get() = _navigateToHome
    val showToast: StateFlow<String> get() = _showToast

    override fun onCodeInputSuccessful() {
        successMessage.set(StringUtil.getString(R.string.code_successful))
        _navigateToHome.value = true
    }

    override fun onFingerprintSuccessful() {
        successMessage.set(StringUtil.getString(R.string.finger_print_success))
        _navigateToHome.value = true
    }

    override fun onPinLoginFailed() {
        _showToast.value = StringUtil.getString(R.string.pin_failed)
    }

    override fun onFingerprintLoginFailed() {
        _showToast.value = StringUtil.getString(R.string.fingerprint_failed)
    }
}