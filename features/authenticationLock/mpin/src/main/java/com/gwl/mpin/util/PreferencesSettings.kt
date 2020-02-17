package com.gwl.mpin.util

import android.content.Context


object PreferencesSettings {

    private val PREF_FILE = "settings_pref"

    fun saveToPref(context: Context, str: String) {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("code", str)
        editor.apply()
    }

    fun getCode(context: Context): String? {
        val sharedPref = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE)
        val defaultValue = ""
        return sharedPref.getString("code", defaultValue)
    }

}
