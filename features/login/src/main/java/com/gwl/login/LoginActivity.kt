package com.gwl.login

import android.os.Bundle
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import com.gwl.core.setKeyboardShown
import com.gwl.core.showSnackbar
import com.gwl.login.databinding.ActivityLoginBinding
import com.gwl.navigation.features.FeedNavigation
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)

    }

    override fun getViewModel(): LoginViewModel =
        initViewModel { LoginViewModel(LoginRepository()) }

    override fun initObservers() {
        super.initObservers()
        mViewModel.apply {
            navigateOnNext.observe {
                FeedNavigation.dynamicStart?.let { startActivity(it) }
                finish()
            }
            onLoginError.observe { container?.showSnackbar(it) }
            showKeyboard.observe { container?.setKeyboardShown(this@LoginActivity, it) }
        }
    }
}

