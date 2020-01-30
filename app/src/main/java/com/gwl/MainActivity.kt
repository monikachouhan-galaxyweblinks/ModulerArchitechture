package com.gwl

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.gwl.navigation.PinLoginNavigation
import com.gwl.navigation.features.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var splitInstallManager: SplitInstallManager
    lateinit var request: SplitInstallRequest
    val DYNAMIC_FEATURE = ":features:player_feeds"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDynamicModules()
        btnFingerPrint?.setOnClickListener { startFingerPrint() }

        btnFeed?.setOnClickListener { startLogin() }
        btnVideoPlayer?.setOnClickListener { startPlayerFeed() }
        btnPinLogin?.setOnClickListener { startPinLogin() }
        btnGoogleLogin?.setOnClickListener { startGoogleLogin() }
        btnFacebookLogin?.setOnClickListener { startFacebookLogin() }

    }

    private fun initDynamicModules() {
        splitInstallManager = SplitInstallManagerFactory.create(this)
        request = SplitInstallRequest
            .newBuilder()
            .addModule(DYNAMIC_FEATURE)
            .build()
    }

    private fun downloadFeature(onSuccess: () -> Unit) {
        splitInstallManager.startInstall(request)
            .addOnFailureListener {
                Log.d("MainActivity", it.localizedMessage.toString())
            }
            .addOnSuccessListener {
                onSuccess()
                Log.d("MainActivity", it.toString())
            }
            .addOnCompleteListener {
                Log.d("MainActivity", it.result.toString())

            }
        var mySessionId = 0
        val listener = SplitInstallStateUpdatedListener {
            mySessionId = it.sessionId()
            when (it.status()) {
                SplitInstallSessionStatus.DOWNLOADING -> {
                    val totalBytes = it.totalBytesToDownload()
                    val progress = it.bytesDownloaded()
                    // Update progress bar.
                }
                SplitInstallSessionStatus.INSTALLING -> Log.d("Status", "INSTALLING")
                SplitInstallSessionStatus.INSTALLED -> Log.d("Status", "INSTALLED")
                SplitInstallSessionStatus.FAILED -> Log.d("Status", "FAILED")
                SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> startIntentSender(
                    it.resolutionIntent().intentSender,
                    null,
                    0,
                    0,
                    0
                )

            }

        }
    }

    private fun isDynamicFeatureDownloaded(feature: String): Boolean =
        splitInstallManager.installedModules.contains(feature)


    private fun uninstallDynamicFeature(list: List<String>) {
        splitInstallManager.deferredUninstall(list)
            .addOnSuccessListener {
                Log.d("MainActivity", "uninstallDynamicFeature ")
            }
    }


    private fun startPlayerFeed() = PlayerNavigation.dynamicStart?.let { startActivity(it) }
    private fun startLogin() = LoginNavigation.dynamicStart?.let { startActivity(it) }
    private fun startFingerPrint() = FingerLockNavigation.dynamicStart?.let { startActivity(it) }
    private fun startPinLogin() = PinLoginNavigation.dynamicStart?.let { startActivity(it) }
    private fun startGoogleLogin() = GoogleLoginNavigation.dynamicStart?.let { startActivity(it) }
    private fun startFacebookLogin() =
        FacebookLoginNavigation.dynamicStart?.let { startActivity(it) }

    companion object {
        private const val HOME = 100
    }
}
