package com.acms.whatnow

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acms.whatnow.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedMode = prefs.getInt(
            "mode",
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
        AppCompatDelegate.setDefaultNightMode(savedMode)

        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // Adjust padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        // ---- Toolbar with back button ----
//        setSupportActionBar(binding.categoryToolbar)
//        binding.categoryToolbar.setNavigationOnClickListener {
//            finish() // Go back to previous activity (LoginActivity if coming from there)
//        }
//
//        // ---- Settings button ----
//        binding.settingsButton.setOnClickListener {
//            startActivity(Intent(this, SettingsActivity::class.java))
//        }

        // load toolbar
        val toolbar: Toolbar = findViewById(R.id.topmenu)
        setSupportActionBar(toolbar)


        // ---- Category buttons ----
        binding.sportsButton.setOnClickListener { openCategory("sports") }
        binding.healthButton.setOnClickListener { openCategory("health") }
        binding.technologyButton.setOnClickListener { openCategory("technology") }
        binding.scienceButton.setOnClickListener { openCategory("science") }
        binding.businessButton.setOnClickListener { openCategory("business") }
        binding.entertainmentButton.setOnClickListener { openCategory("entertainment") }
        binding.generalButton.setOnClickListener { openCategory("general") }


    }


    // load toolbar menu first
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    // handling pressed icons in toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            // favorite list icon handler
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }

            // when more_menu icon pressed--> popup_menu appears
            R.id.action_more -> {
                val moreItemView = findViewById<Toolbar>(R.id.topmenu)
                val popupMenu = PopupMenu(this, moreItemView)


                popupMenu.menuInflater.inflate(R.menu.more_menu, popupMenu.menu)

                // popup menu handler
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {

                        R.id.action_setting -> {
                            val intent = Intent(this, SettingsActivity::class.java)
                            startActivity(intent)
                            true
                        }

                        R.id.action_logout -> {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                            true
                        }

                        else -> false
                    }
                }

                // show popup menu
                popupMenu.show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }


    }

    override fun onResume() {
        super.onResume()
        loadAndDisplayCountry()
    }

    private fun loadAndDisplayCountry() {
        // Get selected country from SharedPreferences set in SettingsActivity
        val prefs = getSharedPreferences("NewRegion", MODE_PRIVATE)
        val savedCountry = prefs.getString("countryCode", "ww")

        binding.showCountryName.text = "Current Region: $savedCountry"

        val countryCode = when (savedCountry) {
            "United States" -> "us"
            "Germany" -> "de"
            "Spain" -> "es"
            "Italy" -> "it"
            "France" -> "fr"
            "England" -> "gb"
            "Russia" -> "ru"
            else -> "ww"
        }

        binding.showSelectedCountryCode.text = "Short Name: $countryCode"
    }

    private fun getCountryCode(): String {
        val prefs = getSharedPreferences("NewRegion", MODE_PRIVATE)
        return when (prefs.getString("countryCode", "ww")) {
            "United States" -> "us"
            "Germany" -> "de"
            "Spain" -> "es"
            "Italy" -> "it"
            "France" -> "fr"
            "England" -> "gb"
            "Russia" -> "ru"
            else -> "ww"
        }
    }

    private fun openCategory(category: String) {
        val intent = Intent(this, NewsActivity::class.java)
        intent.putExtra("category", category.lowercase())
        intent.putExtra("country", getCountryCode())
        startActivity(intent)
    }
}
