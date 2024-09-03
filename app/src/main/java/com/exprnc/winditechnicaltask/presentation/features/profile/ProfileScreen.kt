package com.exprnc.winditechnicaltask.presentation.features.profile

import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Screen

class ProfileScreen : Screen<Int>(
    route = R.id.profile_nav_graph,
    requestKey = TAG
) {
    companion object {
        const val TAG = "ProfileScreen"
    }
}