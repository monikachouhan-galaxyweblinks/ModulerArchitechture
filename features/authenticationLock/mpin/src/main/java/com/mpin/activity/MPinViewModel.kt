package com.mpin.activity

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gwl.core.BaseViewModel
import com.gwl.core.StringUtil
import com.mpin.R
import com.mpinlock.fragments.PFLockScreenFragment

/**
 * @author GWL
 */
class MPinViewModel : BaseViewModel(), PFLockScreenFragment.OnPFLockScreenLoginListener {

    private var _showToast: MutableLiveData<String> = MutableLiveData()
    private var _navigateToHome: MutableLiveData<Boolean> = MutableLiveData()
    private var successMessage = ObservableField("")
    val navigateToHome: LiveData<Boolean> get() = _navigateToHome
    val showToast: LiveData<String> get() = _showToast

    override fun onCodeInputSuccessful() {
        successMessage.set(StringUtil.getString(R.string.code_successful))
        _navigateToHome.postValue(true)
    }

    override fun onFingerprintSuccessful() {
        successMessage.set(StringUtil.getString(R.string.finger_print_success))
        _navigateToHome.postValue(true)
    }

    override fun onPinLoginFailed() {
        _showToast.postValue(StringUtil.getString(R.string.pin_failed))
    }

    override fun onFingerprintLoginFailed() {
        _showToast.postValue(StringUtil.getString(R.string.fingerprint_failed))
    }
}