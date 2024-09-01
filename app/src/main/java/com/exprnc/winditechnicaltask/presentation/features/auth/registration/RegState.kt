package com.exprnc.winditechnicaltask.presentation.features.auth.registration

import com.exprnc.winditechnicaltask.core.ViewState

sealed interface RegState : ViewState {
    class DefaultState(val phone: String, val name: String, val username: String) : RegState
}