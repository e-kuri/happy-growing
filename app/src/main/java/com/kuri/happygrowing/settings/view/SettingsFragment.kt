package com.kuri.happygrowing.settings.view

import android.os.Bundle
import android.text.InputType
import androidx.lifecycle.ViewModelProviders
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.kuri.happygrowing.R
import com.kuri.happygrowing.settings.viewmodel.SettingsViewModel
import com.kuri.happygrowing.settings.viewmodel.SettingsViewModelFactory
import com.kuri.happygrowing.shared.DbConstants

class SettingsFragment: PreferenceFragmentCompat() {

    lateinit var viewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        viewModel = ViewModelProviders.of(activity, SettingsViewModelFactory.create(SettingsViewModel::class.java))
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

    private fun setNumericInputType(vararg preferences: EditTextPreference?) {
        for(preference in preferences) {
            preference?.setOnBindEditTextListener { editText ->
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
            preference?.setOnBindEditTextListener {

            }
        }
    }
    
}