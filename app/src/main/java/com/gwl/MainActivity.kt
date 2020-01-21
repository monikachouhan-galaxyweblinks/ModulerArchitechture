package com.gwl

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gwl.navigation.features.LoginNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // startLogin()
    }

    private fun startLogin() = LoginNavigation.dynamicStart?.let {
        val intent = Intent(this@MainActivity, Class.forName("com.feed.FeedActivity"))
        startActivity(intent)
//        startActivity(it)
    }

    companion object {
        private const val HOME = 100
    }
}
