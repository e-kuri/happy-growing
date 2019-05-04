package com.kuri.happygrowing.settings.view

import android.os.Bundle
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.kuri.happygrowing.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val minTempPref = findPreference<EditTextPreference>("min_temp")
        minTempPref?.setOnPreferenceChangeListener{ _, newValue ->
            Toast.makeText(context, newValue.toString(), Toast.LENGTH_SHORT).show()
            true
        }
    }
    
}