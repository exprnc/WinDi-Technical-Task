package com.exprnc.winditechnicaltask.utils

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.exprnc.winditechnicaltask.core.Screen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun NavController.navigateViaScreenRoute(
    screen: Screen<*>,
    extras: Navigator.Extras? = null
) {
    try {
        val options = NavOptions.Builder().build()
        when (screen.route) {
            is String -> navigate(Uri.parse(screen.route), options, extras)
            is NavDirections -> navigate(screen.route, options)
            is Int -> navigate(screen.route, screen.args, options)
            else -> throw IllegalArgumentException("Unsupported route type (${screen.route})")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Date.toString(pattern: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(pattern, locale)
    return formatter.format(this)
}

inline fun <reified T : Parcelable> Bundle.getParcel(key: String) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelable(key, T::class.java)
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key)
    }