package com.gwl.fingerLock

import android.Manifest
import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.fingure.BR
import com.fingure.R
import com.fingure.databinding.ActivityFingerPrintBinding
import com.gwl.core.BaseActivity
import com.gwl.core.initViewModel
import kotlinx.android.synthetic.main.activity_finger_print.*
import java.io.IOException
import java.security.*
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

/**
 * @author GWL
 */
class FingerPrintActivity : BaseActivity<ActivityFingerPrintBinding, FingerPrintViewModel>() {
    /**
     * This class represents a storage facility for cryptographic keys and certificates.
     */
    private var keyStore: KeyStore? = null
    // The provider implementation (delegate)
    private var cipher: Cipher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding.setVariable(BR.viewModel, mViewModel)
        // Initializing both Android Keyguard Manager and Fingerprint Manager
        initializeObjerbers()
        initializeFingerPrint()
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_finger_print
    }

    override fun getViewModel(): FingerPrintViewModel {
        return initViewModel {
            FingerPrintViewModel()
        }
    }

    private fun initializeObjerbers() {
        mViewModel.errorMessage.set(getString(R.string.desc_fingerprint))
    }

    private fun initializeFingerPrint() {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        val fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        // Check whether the device has a Fingerprint sensor.
        if (!fingerprintManager.isHardwareDetected) {
            /**
             * An error message will be displayed if the device does not contain the fingerprint hardware.
             * However if you plan to implement a default authentication method,
             * you can redirect the user to a default authentication activity from here.
             * Example:
             * Intent intent = new Intent(this, DefaultAuthenticationActivity.class);
             * startActivity(intent);
             */
            errorText.text = getString(R.string.finger_print_not_supported)
        } else {
            // Checks whether fingerprint permission is set on manifest
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED
            ) errorText.text = getString(R.string.finger_print_permission_not_enabled)
            else {
                // Check whether at least one fingerprint is registered
                if (!fingerprintManager.hasEnrolledFingerprints())
                    errorText.text = getString(R.string.register_atleast_one_fingerprint)
                else {
                    // Checks whether lock screen security is enabled or not
                    if (!keyguardManager.isKeyguardSecure)
                        errorText.text = getString(R.string.lock_screen_security_not_enabled)
                    else {
                        generateKey()
                        if (cipherInit()) {
                            val cryptoObject = FingerprintManager.CryptoObject(cipher!!)
                            val helper = FingerprintHandler(
                                context = this@FingerPrintActivity, manager = fingerprintManager,
                                cryptoObject = cryptoObject, viewModel = mViewModel
                            )
                            helper.startAuth()
                        }
                    }
                }
            }
        }
    }

    /*
    *  Generate android key store
    */
    @TargetApi(Build.VERSION_CODES.M)
    protected fun generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val keyGenerator: KeyGenerator
        try {
            keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        } catch (e: NoSuchProviderException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        }
        try {
            keyStore?.load(null)
            keyGenerator.init(
                KeyGenParameterSpec.Builder(
                    KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build()
            )
            keyGenerator.generateKey()
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw RuntimeException(e)
        } catch (e: CertificateException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    fun cipherInit(): Boolean {
        try {
            cipher = Cipher.getInstance(
                KeyProperties.KEY_ALGORITHM_AES +
                        "/" + KeyProperties.BLOCK_MODE_CBC + "/" +
                        KeyProperties.ENCRYPTION_PADDING_PKCS7
            )
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get Cipher", e)
        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e)
        }
        try {
            keyStore!!.load(null)
            val key = keyStore!!.getKey(KEY_NAME, null) as SecretKey
            cipher!!.init(Cipher.ENCRYPT_MODE, key)
            return true
        } catch (e: KeyPermanentlyInvalidatedException) {
            return false
        } catch (e: KeyStoreException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: CertificateException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: UnrecoverableKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: IOException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to init Cipher", e)
        } catch (e: InvalidKeyException) {
            throw RuntimeException("Failed to init Cipher", e)
        }
    }

    companion object {
        // Variable used for storing the key in the Android Keystore container
        private val KEY_NAME = "androidHive"
    }
}