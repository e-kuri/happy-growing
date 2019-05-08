package com.kuri.happygrowing.settings.view

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.kuri.happygrowing.R
import com.kuri.happygrowing.settings.model.Settings
import com.kuri.happygrowing.settings.viewmodel.SettingsViewModel
import com.kuri.happygrowing.shared.viewmodel.ViewModelFactory

class SettingsFragment: PreferenceFragmentCompat() {

    private lateinit var viewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        viewModel = ViewModelProviders.of(this, ViewModelFactory()).get(SettingsViewModel::class.java)
        setPreferencesFromResource(R.xml.preferences, rootKey)

        findPreference<EditTextPreference>("minTemp")?.setOnPreferenceChangeListener{ _, newValue ->
            if(!viewModel.setFloatPreference(Settings::minTemp.name, newValue as String)) {
                Toast.makeText(context, getString(R.string.message_invalid_numeric_string), Toast.LENGTH_SHORT).show()
                false
            } else true
        }

        findPreference<EditTextPreference>("maxTemp")?.setOnPreferenceChangeListener{ _, newValue ->
            if(!viewModel.setFloatPreference(Settings::maxTemp.name, newValue as String)) {
                Toast.makeText(context, getString(R.string.message_invalid_numeric_string), Toast.LENGTH_SHORT).show()
                false
            } else true
        }

        findPreference<EditTextPreference>("minHum")?.setOnPreferenceChangeListener{ _, newValue ->
            if(!viewModel.setFloatPreference(Settings::minHum.name, newValue as String)) {
                Toast.makeText(context, getString(R.string.message_invalid_numeric_string), Toast.LENGTH_SHORT).show()
                false
            } else true
        }

        findPreference<EditTextPreference>("maxHum")?.setOnPreferenceChangeListener{ _, newValue ->
            if(!viewModel.setFloatPreference(Settings::maxHum.name, newValue as String)) {
                Toast.makeText(context, getString(R.string.message_invalid_numeric_string), Toast.LENGTH_SHORT).show()
                false
            } else true
        }
    }
    
}