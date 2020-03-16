package com.gwl.home

import com.gwl.MyApplication
import com.gwl.core.BaseViewModel
import com.gwl.model.User

/**
 * @author GWL
 */
class HomeViewModel : BaseViewModel() {
    val user: User? by lazy { MyApplication.loginManager.getUser() }

}