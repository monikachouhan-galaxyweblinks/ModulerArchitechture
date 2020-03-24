package com.gwl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gwl.navigation.features.LoginNavigation
import com.gwl.navigation.features.SearchNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startLogin()
    }

    private fun startLogin() = SearchNavigation.dynamicStart?.let {
        startActivity(it)
        finish()
    }
}
