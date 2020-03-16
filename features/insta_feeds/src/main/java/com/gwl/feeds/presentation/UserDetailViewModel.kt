package com.gwl.feeds.presentation

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.gwl.MyApplication
import com.gwl.core.BaseViewModel
import com.gwl.core.Common
import com.gwl.core.LoginManager
import com.gwl.model.InstaFeed
import com.gwl.model.UserField

/**
 * @author GWL
 */
class UserDetailViewModel : BaseViewModel() {
    var instaFeed: InstaFeed? = null
    val isEditable: ObservableBoolean by lazy { ObservableBoolean(false) }
    val loginManager: LoginManager by lazy { MyApplication.loginManager }
    

    fun onFocusChanged(view: View, hasFocus: Boolean) {
        Common.showKeyboard(view, hasFocus)

    }
}