package com.kuri.happygrowing.settings.view

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.kuri.happygrowing.R
import com.kuri.happygrowing.settings.viewmodel.SettingsViewModel
import com.kuri.happygrowing.shared.DbConstants

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        initializeNumericPreferences()
    }

    /**
     * Initializes the numeric preferences by allowing only numbers in them.
     */
    private fun initializeNumericPreferences() {
        val minTemp = findPreference<EditTextPreference>(DbConstants.SETTINGS_MIN_TEMP_KEY)
        val maxTemp = findPreference<EditTextPreference>(DbConstants.SETTINGS_MAX_TEMP_KEY)
        val minHum = findPreference<EditTextPreference>(DbConstants.SETTINGS_MIN_HUM_KEY)
        val maxHum = findPreference<EditTextPreference>(DbConstants.SETTINGS_MAX_HUM_KEY)
        setNumericInputType(minTemp, maxTemp, minHum, maxHum)
    }

    /**
     * Initializes EditTextPreferences to allow nmeric only numbers and sets their onchange listeners
     * to set preferences on the settingsViewModel.
     * @param preferences EditTextPreferences that will be set as numeric only.
     */
    private fun setNumericInputType(vararg preferences: EditTextPreference?) {
        for(preference in preferences) {
            preference?.setOnBindEditTextListener { editText ->
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
        }
        for(preference in preferences) {
            preference?.setOnPreferenceChangeListener { preference: Preference, newValue: Any ->
                if(preference.key.toFloatOrNull() == null) false
                SettingsViewModel.setPreference(preference.key, newValue.toString())
                true
            }
        }
    }
    
}