package com.gwl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.gwl.navigation.PinLoginNavigation
import com.gwl.navigation.features.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var splitInstallManager: SplitInstallManager
    lateinit var request: SplitInstallRequest
    val DYNAMIC_FEATURE = ":features:player_feeds"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLogin()

        setContentView(R.layout.activity_main)

        btnFingerPrint?.setOnClickListener { startFingerPrint() }
        btnFeed?.setOnClickListener { startLogin() }
        btnVideoPlayer?.setOnClickListener { startPlayerFeed() }
        btnPinLogin?.setOnClickListener { startPinLogin() }
        btnGoogleLogin?.setOnClickListener { startGoogleLogin() }
        btnFacebookLogin?.setOnClickListener { startFacebookLogin() }
        btnHome?.setOnClickListener { startHome() }
    }

    private fun startPlayerFeed() = PlayerNavigation.dynamicStart?.let { /*startActivity(it)*/ }
    private fun startLogin() = LoginNavigation.dynamicStart?.let {
        startActivity(it)
        finish()
    }

    private fun startFingerPrint() = FingerLockNavigation.dynamicStart?.let { startActivity(it) }
    private fun startPinLogin() = PinLoginNavigation.dynamicStart?.let { startActivity(it) }
    private fun startGoogleLogin() = GoogleLoginNavigation.dynamicStart?.let { startActivity(it) }
    private fun startHome() = HomeNavigation.dynamicStart?.let { startActivity(it) }
    private fun startFacebookLogin() {
        FacebookLoginNavigation.dynamicStart?.let {
            startActivity(it)
        }
    }

    companion object {
        private const val HOME = 100
    }
}
