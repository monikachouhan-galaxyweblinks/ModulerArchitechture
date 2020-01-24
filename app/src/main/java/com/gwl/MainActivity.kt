package com.gwl

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gwl.navigation.features.PlayerNavigation

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startFeed()
    }

    private fun startFeed() = PlayerNavigation.dynamicStart?.let { startActivity(it) }

    companion object {
        private const val HOME = 100
    }
}
