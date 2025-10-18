package com.acms.whatnow

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import com.acms.whatnow.databinding.ActivitySettingsBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class SettingsActivity : AppCompatActivity() {
    private lateinit var prefs: SharedPreferences
    private var selectedCountry: String? = null
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val savedMode = prefs.getInt("mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        val savedLang = prefs.getString("lang", "en")
        selectedCountry = prefs.getString("countryCode", "ww")

        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(savedLang))
        AppCompatDelegate.setDefaultNightMode(savedMode)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupCountrySpinner()
        binding.logOutBtn.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
        binding.theme.setOnClickListener {
            modeSetupDialog(getString(R.string.choose_theme))
        }
        binding.lang.setOnClickListener {
            langSetupDialog(getString(R.string.choose_lang))
        }

    }
    private fun setupCountrySpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val countriesList = ArrayAdapter.createFromResource(
            this,
            R.array.countries_array,
            android.R.layout.simple_spinner_item
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        // Apply the adapter to the spinner
        binding.countriesSpinner.adapter = countriesList
        //------------------------------------------------------
        val savedCode = prefs.getString("countryCode" , "ww")
        // Preventing the onItemSelected listener from auto selection when the activity created
        // Getting the position of the saved country in the adapter
        val initialPosition = countriesList.getPosition(savedCode)
        if (initialPosition >= 0) {
            binding.countriesSpinner.setSelection(initialPosition , false)
        }
        val countryName = when (savedCode) {
            "us" -> getString(R.string.usa)
            "de" -> getString(R.string.de)
            "it" -> getString(R.string.it)
            "fr" -> getString(R.string.fr)
            "gb" -> getString(R.string.en)
            "ru" -> getString(R.string.ru)
            else -> getString(R.string.ww)
        }
        //------------------------------------------------------
        binding.countriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedCountry = parent?.getItemAtPosition(position).toString()
                if (savedCode != selectedCountry) {
                    // Getting a reference to the shared preferences
                    val prefs = getSharedPreferences("NewRegion" , MODE_PRIVATE)
                    // Get the editor to modify the preferences
                    val editor = prefs.edit()
                    // Save the country using key-value pair
                    editor.putString("countryCode" , selectedCountry)
                    // Saving changes asynchronously
                    editor.apply()
                    // Toast to confirm your selection
                    Toast.makeText(this@SettingsActivity , "Switched to $selectedCountry" , Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }
    fun langSetupDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)
        dialog.window?.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val body: TextView = dialog.findViewById(R.id.title)
        body.text = title


        val arRg: RadioButton = dialog.findViewById(R.id.second_rb)
        val enRg: RadioButton = dialog.findViewById(R.id.first_rb)
        val gone: RadioButton = dialog.findViewById(R.id.third_rb)

        arRg.text = getString(R.string.arabic)
        enRg.text = getString(R.string.english)
        gone.isGone = true

        val cancelButton: Button = dialog.findViewById(R.id.cancelBtn)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        val okButton: Button = dialog.findViewById(R.id.saveBtn)
        val editor = prefs.edit()
        val savedLang = prefs.getString("lang", "en")

        when (savedLang) {
            "ar" -> arRg.isChecked = true
            else -> enRg.isChecked = true
        }
        okButton.setOnClickListener {
            val langCode = if (arRg.isChecked) "ar" else "en"
            val savedLang = prefs.getString("lang", "en")

            if (langCode != savedLang) {
                editor.putString("lang", langCode).apply()
                val localeList = LocaleListCompat.forLanguageTags(langCode)
                AppCompatDelegate.setApplicationLocales(localeList)
                dialog.dismiss()
                recreate()
            }
        }
        dialog.show()
    }

    fun modeSetupDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog)
        dialog.window?.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val body: TextView = dialog.findViewById(R.id.title)
        body.text = title


        val sdRg: RadioButton = dialog.findViewById(R.id.first_rb)
        val dRg: RadioButton = dialog.findViewById(R.id.second_rb)
        val lRg: RadioButton = dialog.findViewById(R.id.third_rb)

        val okButton: Button = dialog.findViewById(R.id.saveBtn)
        val editor = prefs.edit()

        when (prefs.getInt("mode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)) {
            AppCompatDelegate.MODE_NIGHT_YES -> dRg.isChecked = true
            AppCompatDelegate.MODE_NIGHT_NO -> lRg.isChecked = true
            else -> sdRg.isChecked = true
        }


        okButton.setOnClickListener {
            val mode = if (dRg.isChecked)
                AppCompatDelegate.MODE_NIGHT_YES
            else if (lRg.isChecked)
                AppCompatDelegate.MODE_NIGHT_NO
            else
                AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM

            editor.putInt("mode", mode).apply()
            AppCompatDelegate.setDefaultNightMode(mode)
            dialog.dismiss()
        }

        val cancelButton: Button = dialog.findViewById(R.id.cancelBtn)
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}