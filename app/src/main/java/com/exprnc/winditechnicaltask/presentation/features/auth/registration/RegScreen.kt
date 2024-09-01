package com.exprnc.winditechnicaltask.presentation.features.auth.registration

import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Screen

class RegScreen(
    args: RegArgs
) : Screen<Int>(
    route = R.id.regFragment,
    args = args.toBundle(),
    requestKey = TAG
) {
    companion object {
        const val TAG = "RegScreen"
    }
}