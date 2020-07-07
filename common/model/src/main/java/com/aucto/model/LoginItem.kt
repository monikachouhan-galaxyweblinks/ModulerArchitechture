package com.aucto.model

data class LoginItem(val id: Int = 0, var email: String, var password: String) {

    var isValidCredential: Boolean = false
        get() = (email == "aucto@example.com" && password == "Gwl@1234")

}