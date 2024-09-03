package com.exprnc.winditechnicaltask.presentation.features.profile

import com.exprnc.winditechnicaltask.core.ViewState
import com.exprnc.winditechnicaltask.domain.model.User

sealed interface ProfileState : ViewState {
    class DefaultState(val user: User, val expanded: Boolean) : ProfileState
}