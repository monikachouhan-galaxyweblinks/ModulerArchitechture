package com.googlelogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import com.gwl.MyApplication
import com.gwl.model.User
import com.gwl.navigation.features.HomeNavigation
import kotlinx.android.synthetic.main.activity_google_login.*

class GoogleLoginActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

    private lateinit var mAuthListener: AuthStateListener
    private lateinit var mGoogleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 9001
    private val mAuth = FirebaseAuth.getInstance()
    private val loginManager by lazy { MyApplication.loginManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)
        mAuthListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                // User is signed in
                Log.e("LOG_CAT", "onAuthStateChanged:signed_in:" + user.uid)
            } else { // User is signed out
                Log.e("LOG_CAT", "onAuthStateChanged:signed_out")
            }
        }
        initForLogin()
        googleSignIn()
    }

    private fun initForLogin() {
        googlePreInit()
    }

    private fun googleSignIn() {
        val signInIntent =
            Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun googlePreInit() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(com.gwl.R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()
    }

    private fun signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
    }

    override fun onConnected(bundle: Bundle?) {
        //called when connected to google client
    }

    override fun onConnectionSuspended(i: Int) {
        //called when google client connection Suspended
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
       //Log.e(javaClass.name, "onConnectionFailed:$connectionResult")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            /**
             * called on login result
             */
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.e(
                "resulttt ::: ", " onActivityResult  -- ${result.signInAccount} "
                        + result.isSuccess.toString()
            )
            if (result.isSuccess) {
                val account = result.signInAccount
                if (account?.displayName != null) {
                    val userNameStr = account.displayName
                    tvName.text = userNameStr
                    Log.e(
                        "resulttt  name ::: ",
                        userNameStr + "  ${account.email}" + "  ${account.photoUrl}"
                    )
                }
                val user = User().apply {
                    name = account?.displayName ?: ""
                    email = account?.email ?: ""
                    profileUrl = account?.photoUrl.toString() ?: ""
                }
                firebaseAuthWithGoogle(account)
                loginManager.setUser(user)
                navigateOnHome()
            }
        } else {
            Log.e("resulttt ::: data ", data.toString())
        }
        finish()
    }

    private fun navigateOnHome() {
        HomeNavigation.dynamicStart?.let {
            startActivity(it)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mAuthListener)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this@GoogleLoginActivity) {
            if (!it.isSuccessful) return@addOnCompleteListener
        }
        signOut()
    }
}
