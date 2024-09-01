package com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode

import com.exprnc.winditechnicaltask.core.ViewState

sealed interface VerificationCodeState : ViewState {
    class DefaultState(val phone: String, val code: String) : VerificationCodeState
}