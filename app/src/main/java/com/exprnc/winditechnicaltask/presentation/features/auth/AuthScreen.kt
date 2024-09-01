package com.exprnc.winditechnicaltask.presentation.features.auth

import android.os.Bundle
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Screen

class AuthScreen : Screen<Int>(
    route = R.id.authFragment,
    requestKey = TAG
) {
    companion object {
        const val TAG = "AuthScreen"
    }
}