package com.gwl.profile

import androidx.databinding.ObservableBoolean
import com.gwl.MyApplication
import com.gwl.core.BaseViewModel
import com.gwl.core.LoginManager
import com.gwl.model.UserField

/**
 * @author GWL
 */
class ProfileViewModel : BaseViewModel() {
    val user = MyApplication.loginManager.getUser()
    val isEditable: ObservableBoolean by lazy { ObservableBoolean(false) }
    val loginManager: LoginManager by lazy { MyApplication.loginManager }

    fun updateUser() {
        loginManager.setUser(user)
    }

    fun afterTextChange(userField: UserField, value: String) {
        when (userField) {
            UserField.EMAIL -> user?.email = value
            UserField.NAME -> user?.name = value
            UserField.MOBILE -> user?.mobile = value
            UserField.CITY -> user?.city = value
            UserField.STATE -> user?.state = value
        }
    }
}