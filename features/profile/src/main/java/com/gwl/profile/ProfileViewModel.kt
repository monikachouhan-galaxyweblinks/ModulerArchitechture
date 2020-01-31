package com.gwl.profile

import com.gwl.MyApplication
import com.gwl.core.BaseViewModel

/**
 * @author GWL
 */
class ProfileViewModel() : BaseViewModel() {
    val user = MyApplication.loginManager.getUser()
}