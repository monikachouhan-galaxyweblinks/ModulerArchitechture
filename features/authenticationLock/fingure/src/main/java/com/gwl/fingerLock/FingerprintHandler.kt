package com.gwl.fingerLock

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.CancellationSignal
import androidx.core.app.ActivityCompat
import com.fingure.R
import com.gwl.navigation.features.HomeNavigation


class FingerprintHandler(
    val context: Context, val manager: FingerprintManager,
    val cryptoObject: FingerprintManager.CryptoObject, val viewModel: FingerPrintViewModel
) : FingerprintManager.AuthenticationCallback() {

    fun startAuth() {
        val cancellationSignal = CancellationSignal()
        if (ActivityCompat.checkSelfPermission(
                context, Manifest.permission.USE_FINGERPRINT
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    /**
     * Called when an unrecoverable error has been encountered and the operation is complete.
     * No further callbacks will be made on this object.
     * @param errMsgId An integer identifying the error message
     * @param errString A human-readable error string that can be shown in UI
     */
    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
        viewModel.errorMessage.set("${context.getString(R.string.finger_print_aithentication_error)}\n$errString")
    }

    /**
     * Called when a recoverable error has been encountered during authentication. The help
     * string is provided to give the user guidance for what went wrong, such as
     * "Sensor dirty, please clean it."
     * @param helpMsgId An integer identifying the error message
     * @param helpString A human-readable string that can be shown in UI
     */
    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
        viewModel.errorMessage.set("${context.getString(R.string.finger_print_aithentication_help)}\n$helpString")
    }

    /**
     * Called when a fingerprint is valid but not recognized.
     */
    override fun onAuthenticationFailed() {
        viewModel.errorMessage.set(context.getString(R.string.finger_print_aithentication_failed))
    }

    /*
    *  Handle redirection after finger print authentication success
    * */
    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult) {
        context.startActivity(HomeNavigation.dynamicStart)
        (context as Activity).finish()
    }

}
