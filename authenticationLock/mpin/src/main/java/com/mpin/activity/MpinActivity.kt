package com.mpin.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.navigation.features.HomeNavigation
import com.mpin.R
import com.mpin.databinding.ActivityMpinBinding
import com.mpin.util.PreferencesSettings
import com.mpinlock.BR
import com.mpinlock.PFFLockScreenConfiguration
import com.mpinlock.fragments.PFLockScreenFragment
import com.mpinlock.viewmodels.PFPinCodeViewModel

class MpinActivity : BaseActivity<ActivityMpinBinding, MPinViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        initializeObservers()
        showLockScreenFragment()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_mpin
    }

    override fun getViewModel(): MPinViewModel {
        return initViewModel {
            MPinViewModel()
        }
    }

    private fun initializeObservers() {
        mViewModel.showToast.observe {
            showToast(it)
        }
        mViewModel.navigateToHome.observe { redirectToHomeScreen() }
    }

    private fun showLockScreenFragment() {
        PFPinCodeViewModel().isPinCodeEncryptionKeyExist.observe(
            this,
            Observer { result ->
                if (result == null) return@Observer
                if (result.error != null) {
                    showToast(getString(R.string.can_not_get_pin_info))
                    return@Observer
                }
                showLockScreenFragment(result.result)
            }
        )
    }

    private fun showLockScreenFragment(isPinExist: Boolean) {
        val builder = PFFLockScreenConfiguration.Builder(this)
            .setTitle(
                if (isPinExist) getString(R.string.unloack_with_pin_or_fingerprint) else getString(
                    R.string.create_code
                )
            )
            .setCodeLength(4)
            .setLeftButton(getString(R.string.not_remember))
            .setNewCodeValidation(true)
            .setNewCodeValidationTitle(getString(R.string.please_input_code_again))
            .setUseFingerprint(true)
        val fragment = PFLockScreenFragment()
        fragment.setOnLeftButtonClickListener(View.OnClickListener {
            showToast(getString(R.string.left_button_pressed))
        })
        builder.setMode(
            if (isPinExist) PFFLockScreenConfiguration.MODE_AUTH
            else PFFLockScreenConfiguration.MODE_CREATE
        )
        if (isPinExist) {
            fragment.setEncodedPinCode(PreferencesSettings.getCode(this) ?: "")
            fragment.setLoginListener(mViewModel)
        }
        fragment.setConfiguration(builder.build())
        fragment.setCodeCreateListener(mCodeCreateListener)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_view, fragment).commit()
    }

    private val mCodeCreateListener =
        object : PFLockScreenFragment.OnPFLockScreenCodeCreateListener {
            override fun onCodeCreated(encodedCode: String) {
                showToast(getString(R.string.code_created))
                PreferencesSettings.saveToPref(this@MpinActivity, encodedCode)
            }

            override fun onNewCodeValidationFailed() {
                showToast(getString(R.string.code_validation_error))
            }
        }


    private fun redirectToHomeScreen() {
        startActivity(HomeNavigation.dynamicStart)
        finishAffinity()
    }

    fun showToast(msg: String) {
        Toast.makeText(this@MpinActivity, msg, Toast.LENGTH_SHORT).show()
    }
}
