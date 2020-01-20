package com.gwl.core

import android.content.Context
import android.content.SharedPreferences

class LoginManager private constructor(context: Context) {
    companion object {
        private const val SHARED_PREFS_NAME = "Ulltma_APP"
        private const val SHARED_PREFS_KEY_USER = "user"
        private var instance: LoginManager? = null

        fun getInstance(context: Context): LoginManager {
            return (if (instance != null) instance
            else LoginManager(context)) as LoginManager
        }
    }

    private val sharedPreferences: SharedPreferences
    private var userName: String? = null

    init {
        this.sharedPreferences =
            context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getUserExist(): Boolean {
        return fetchPersistedUser()
    }

    private fun fetchPersistedUser(): Boolean {
        return sharedPreferences.getBoolean(SHARED_PREFS_KEY_USER, false)
    }

    fun persistUser(boolean: Boolean) {
        sharedPreferences.edit()
            .putBoolean(SHARED_PREFS_KEY_USER, boolean)
            .apply()
    }

    fun clearDataForLogout() {
        userName = null
        sharedPreferences.edit()
            .clear()
            .apply()
    }

    fun setInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun setString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean? {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }
}