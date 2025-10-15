package com.acms.whatnow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val mode = prefs.getInt("mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        AppCompatDelegate.setDefaultNightMode(mode)

        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        startActivity(Intent(this, SettingsActivity::class.java))
        finish()
    }
}
