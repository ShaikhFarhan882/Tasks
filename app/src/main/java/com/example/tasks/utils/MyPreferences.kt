package com.example.tasks.utils

import android.content.Context
import androidx.preference.PreferenceManager

class MyPreferences(context: Context?) {

    companion object {
        private const val CURRENT_THEME = "current_theme_status"
    }

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context!!)

    var darkMode = preferences.getInt(CURRENT_THEME, 2)
        set(value) = preferences.edit().putInt(CURRENT_THEME, value).apply()

}