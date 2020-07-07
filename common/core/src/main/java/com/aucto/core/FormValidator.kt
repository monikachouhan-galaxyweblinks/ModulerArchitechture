package com.aucto.core

class FormValidator {
    // region - Companion object
    companion object {
        // region - Public function
        fun isPasswordValid(password: String): Boolean {
            return password.matches("(?=.*[a-zA-Z])(?=.*\\d).{4,15}".toRegex()) || password.matches(
                "(?=.*[a-zA-Z]).{4,15}".toRegex()
            ) || password.matches("(?=.*\\d).{4,15}".toRegex())
        }

        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isEmptyValidation(name: String): Boolean {
            return name.isNotEmpty()
        }

        /*fun getValidationErrorMessage(
            hasFocus: Boolean, model: LoginItem,
            loginField: LoginField,
            showError: (String?) -> Unit
         ) {
             if (model.isValidDetails)
                 return
             if (model.isPasswordValid || model.isEmailValid)
                 showError(null)

             if (loginField == LoginField.USER_NAME && !hasFocus && model.email.isEmpty()) {
                 showError(getContext().getString(R.string.email_empty_error))
             } else if (loginField == LoginField.USER_NAME && !hasFocus && !model.isEmailValid) {
                 showError(getContext().getString(R.string.error_invalid_email))
             } else if (loginField == LoginField.PASSWORD && !hasFocus && model.password.isEmpty()) {
                 showError(getContext().getString(R.string.password_empty_error))
             } else if (loginField == LoginField.PASSWORD && !hasFocus && !model.isPasswordValid) {
                 showError(getContext().getString(R.string.password_invalid_error))
             }
         }*/

        // endregion
    }
    // endregion
}