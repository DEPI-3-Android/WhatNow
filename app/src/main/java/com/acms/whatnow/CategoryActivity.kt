package com.acms.whatnow

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.acms.whatnow.databinding.ActivityCategoryBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.action_more -> {
                val moreItemView = findViewById<Toolbar>(R.id.topmenu)
                val popupMenu = PopupMenu(this, moreItemView)


                popupMenu.menuInflater.inflate(R.menu.more_menu, popupMenu.menu)

                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {

                        R.id.action_setting -> {
                            val intent = Intent(this, SettingsActivity::class.java)
                            startActivity(intent)
                            true
                        }

                        R.id.action_logout -> {
                            Firebase.auth.signOut()
                            startActivity(Intent(this, SignUpActivity::class.java))
                            finish()
                            true
                        }

                        else -> false
                    }
                }

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
        val savedCountry = prefs.getString("countryCode", "us")
        val displayName = when (savedCountry) {
            "de" -> getString(R.string.de)
            "it" -> getString(R.string.it)
            "fr" -> getString(R.string.fr)
            "gb" -> getString(R.string.en)
            "ru" -> getString(R.string.ru)
            else -> getString(R.string.usa)
        }
        binding.showCountryName.text = getString(R.string.curr_region) + " $displayName"
        binding.showSelectedCountryCode.text = getString(R.string.short_name) + " $savedCountry"
    }

    private fun getCountryCode(): String {
        return prefs.getString("countryCode", "us") ?: "us"
    }


    private fun openCategory(category: String) {
        val intent = Intent(this, NewsActivity::class.java)
        intent.putExtra("category", category.lowercase())
        intent.putExtra("country", prefs.getString("countryCode", "us"))
        startActivity(intent)
    }
}
