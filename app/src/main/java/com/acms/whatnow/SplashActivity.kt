package com.acms.whatnow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SplashActivity : ComponentActivity() {
    public lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val mode = prefs.getInt("mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        AppCompatDelegate.setDefaultNightMode(mode)

        val splashScreen = installSplashScreen()

        auth = Firebase.auth


        super.onCreate(savedInstanceState)
        val intent = if (auth.currentUser == null) {
            Intent(this, SignUpActivity::class.java)
        } else {
            Intent(this, MainActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()

    }
}
