package com.google_login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
/*import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult*/
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_google_login.*

class GoogleLoginActivity : AppCompatActivity(), View.OnClickListener,
    GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private lateinit var mAuthListener: AuthStateListener
    private lateinit var mGoogleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 9001
    private val mAuth = FirebaseAuth.getInstance()
    private val mContext: Context = this
    // private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)
        //        callbackManager = CallbackManager.Factory.create();
        mAuthListener = AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) { // User is signed in
                Log.e("LOG_CAT", "onAuthStateChanged:signed_in:" + user.uid)
            } else { // User is signed out
                Log.e("LOG_CAT", "onAuthStateChanged:signed_out")
            }
        }
        initForLogin()
        googleSignIn()
        /*FacebookSdk.sdkInitialize(this)
        getFacebookUserInfo()*/
    }

    private fun initForLogin() {
        googlePreInit()
    }

    override fun onClick(v: View) {
    }

    private fun googleSignIn() {
        val signInIntent =
            Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun googlePreInit() {
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
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
        Log.e("LOG_CAT", "onConnected: $bundle")
    }

    override fun onConnectionSuspended(i: Int) {
        Log.e("LOG_CAT", "onConnectionSuspended: $i")
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Log.e(javaClass.name, "onConnectionFailed:$connectionResult")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            Log.e("resulttt ::: ", result.toString())
            if (result.isSuccess) {
                val account = result.signInAccount
                if (account!!.displayName != null) {
                    val userNameStr = account.displayName
                    tvName.setText(userNameStr)
                    Log.e(
                        "resulttt  name ::: ",
                        userNameStr + "  ${account.email}" + "  ${account.photoUrl}"
                    )
                }
                firebaseAuthWithGoogle(account)
            }
        } else {
            Log.e("resulttt ::: data ", data.toString())
            //callbackManager!!.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(mAuthListener!!)
    }

    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) mAuth.removeAuthStateListener(mAuthListener)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this@GoogleLoginActivity) {
            if (!it.isSuccessful)
                return@addOnCompleteListener
        }
        signOut()
    }


    /* fun getFacebookUserInfo() {
         callbackManager = CallbackManager.Factory.create();
         FacebookSdk.sdkInitialize(this);
         LoginManager.getInstance().logOut()
         LoginManager.getInstance().logInWithReadPermissions(
             this,
             Arrays.asList("public_profile", "user_friends", "email")
         )
         LoginManager.getInstance().registerCallback(callbackManager, object :
             FacebookCallback<LoginResult?> {
             override fun onSuccess(loginResult: LoginResult?) {
                 Log.e("LOG_CAT", loginResult?.accessToken.toString())
                 val request = GraphRequest.newMeRequest(
                     loginResult?.accessToken
                 ) { `object`, response ->
                     Log.v("LOG_CAT", response.toString())
                     try {
                         val userIdStr = `object`.getString("id")
                         val userNameStr = `object`.getString("name")
                         val parts =
                             userNameStr.split("\\s+").toTypedArray()
                         val firstNameStr = parts[0]
                         val lastNameStr = parts[1]
                         val emailIdStr = `object`.getString("email")
                         val profileLinkStr =
                             "http://graph.facebook.com/$userIdStr/picture?type=large"
                     } catch (e: Exception) {
                         Log.e("FBError", e.toString())
                     }
                 }
                 val parameters = Bundle()
                 parameters.putString("fields", "id,name,email,gender,birthday,link")
                 request.parameters = parameters
                 request.executeAsync()
             }

             override fun onCancel() {
                 Log.e("onCancel", "onCancel")
             }

             override fun onError(exception: FacebookException) {
                 Log.e("onError", exception.toString())
             }

         })
     }*/
}
