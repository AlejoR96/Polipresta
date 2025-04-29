package com.poli.polipresta.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

object SessionManager {
    private const val PREF_NAME = "PoliprestaPrefs"
    private const val KEY_IS_LOGGED_IN = "isLoggedIn"
    private const val KEY_CEDULA = "cedula"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveLoginState(context: Context, isLoggedIn: Boolean, cedula: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.putString(KEY_CEDULA, cedula)
        editor.apply()
    }

    fun isUserLoggedIn(context: Context): Boolean {
        return getSharedPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun getLoggedInUserCedula(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_CEDULA, null)
    }

    fun logout(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.clear()
        editor.apply()
    }

    @Composable
    fun CheckLoginState(navigateToLogin: () -> Unit) {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            if (!isUserLoggedIn(context)) {
                navigateToLogin()
            }
        }
    }
}