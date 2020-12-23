package com.gwl.profile

import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import com.gwl.MyApplication
import com.gwl.core.BaseViewModel
import com.gwl.core.Common
import com.gwl.core.LoginManager
import com.gwl.model.UserField
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * @author GWL
 */
@ExperimentalCoroutinesApi
class ProfileViewModel : BaseViewModel() {
    val user = MyApplication.loginManager.getUser()
    val isEditable: ObservableBoolean by lazy { ObservableBoolean(false) }
    val loginManager: LoginManager by lazy { MyApplication.loginManager }
    var mPickProfilePicture = MutableStateFlow<Boolean>(false)

    fun updateUser() {
        Log.e("updateUser  ", "${user}")
        loginManager.setUser(user)
    }

    fun afterTextChange(userField: UserField, value: String) {
        when (userField) {
            UserField.EMAIL -> user?.email = value
            UserField.NAME -> user?.name = value
            UserField.MOBILE -> {
                /*val phoneUtil = PhoneNumberUtil.getInstance()
                val formatter = phoneUtil.getAsYouTypeFormatter("US")
               phoneUtil.parse(value,Locale.getDefault().country)*/
                user?.mobile = value

            }
            UserField.CITY -> user?.city = value
            UserField.STATE -> user?.state = value
        }
    }

    fun askToChangeProfile() {
        mPickProfilePicture.value = true
    }

    fun onFocusChanged(view: View, hasFocus: Boolean) {
        Common.showKeyboard(view, hasFocus)

    }
}