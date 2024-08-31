package com.exprnc.winditechnicaltask.utils

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import com.exprnc.winditechnicaltask.core.Screen

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
