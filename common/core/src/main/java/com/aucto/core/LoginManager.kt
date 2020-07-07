package com.aucto.core

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.aucto.model.User

/**
 * Save User information
 * */
const val KEY_AUTO_PLAY_SETTING = "autoplay"
const val KEY_ACCESS_TOKEN = "access_token"
const val KEY_THEME_SETTING = "darktheme"

class LoginManager private constructor(context: Context) {
    companion object {
        private const val SHARED_PREFS_NAME = "Moduler_APP"
        private const val SHARED_PREFS_KEY_USER_EXIST = "user_exist"
        private const val SHARED_PREFS_KEY_USER = "user"
        private var instance: LoginManager? = null
        private val gson = Gson()
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
        return sharedPreferences.getBoolean(SHARED_PREFS_KEY_USER_EXIST, false)
    }

    fun persistUser(boolean: Boolean) {
        sharedPreferences.edit()
            .putBoolean(SHARED_PREFS_KEY_USER_EXIST, boolean)
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

    fun setUser(user: User?) {
        val userString = gson.toJson(user)
        sharedPreferences.edit().putString(SHARED_PREFS_KEY_USER, userString).apply()
    }

    fun getUser(): User? {
        val user = sharedPreferences.getString(SHARED_PREFS_KEY_USER, null)
        return gson.fromJson(user, User::class.java)
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }
}