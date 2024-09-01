package com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode

import com.exprnc.winditechnicaltask.core.Intent

sealed interface VerificationCodeIntent: Intent {
    data object OnNextPressed : VerificationCodeIntent
    class OnCodeInput(val field: String) : VerificationCodeIntent
}