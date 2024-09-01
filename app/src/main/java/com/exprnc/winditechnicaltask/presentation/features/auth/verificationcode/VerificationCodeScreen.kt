package com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode

import android.os.Bundle
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.Screen

class VerificationCodeScreen(
    args: VerificationCodeArgs
) : Screen<Int>(
    route = R.id.verificationCodeFragment,
    args = args.toBundle(),
    requestKey = TAG
) {
    companion object {
        const val TAG = "VerificationCodeScreen"
    }
}