package com.kuri.happygrowing.settings.view

import android.os.Bundle
import android.text.InputType
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat
import com.kuri.happygrowing.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        initializeNumericPreferences()
    }

    /**
     * Initializes the numeric preferences by allowing only numbers in them.
     */
    private fun initializeNumericPreferences() {
        setNumericInputType(findPreference("min_temp"))
        setNumericInputType(findPreference("max_temp"))
        setNumericInputType(findPreference("min_hum"))
        setNumericInputType(findPreference("max_hum"))
    }

    private fun setNumericInputType(preference: EditTextPreference?) {
        preference?.setOnBindEditTextListener { editText ->
            editText.inputType = InputType.TYPE_CLASS_NUMBER
        }
    }
    
}