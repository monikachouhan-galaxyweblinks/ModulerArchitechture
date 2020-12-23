package com.gwl.login

import android.os.Bundle
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.core.setKeyboardShown
import com.gwl.core.showSnackbar
import com.gwl.login.databinding.ActivityLoginBinding
import com.gwl.navigation.features.FacebookLoginNavigation
import com.gwl.navigation.features.GoogleLoginNavigation
import com.gwl.navigation.features.HomeNavigation
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@ExperimentalCoroutinesApi
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
    }

    override fun getViewModel(): LoginViewModel = initViewModel { LoginViewModel() }

    override suspend fun initObservers() {
        super.initObservers()
        mViewModel.apply {
            navigateOnNext.collectLatest { navigateOnHome() }
            navigateForGoogleLogin.collectLatest { navigateOnGoogleLogin() }
            navigateForFacebookLogin.collectLatest { navigateOnFacebookLogin() }
            onLoginError.collectLatest { container?.showSnackbar(it) }
            showKeyboard.collectLatest { container?.setKeyboardShown(this@LoginActivity, it) }
        }
    }

    private fun navigateOnHome() {
        HomeNavigation.dynamicStart?.let {
            startActivity(it)
            finish()
        }
    }

    private fun navigateOnGoogleLogin() {
        GoogleLoginNavigation.dynamicStart?.let {
            startActivity(it)
            finish()
        }
    }

    private fun navigateOnFacebookLogin() {
        FacebookLoginNavigation.dynamicStart?.let {
            startActivity(it)
            finish()
        }
    }
}

