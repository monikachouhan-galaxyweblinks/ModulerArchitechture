package com.gwl.model

data class LoginItem(val id: Int = 0, var email: String, var password: String) {

    var isValidCredential: Boolean = false
        get() = (email == "gwl@example.com" && password == "Gwl@1234")

}