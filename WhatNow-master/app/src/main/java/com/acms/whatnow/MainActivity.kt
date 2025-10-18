package com.acms.whatnow

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import com.acms.whatnow.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedMode = prefs.getInt(
            "mode",
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
        AppCompatDelegate.setDefaultNightMode(savedMode)


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.main.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}