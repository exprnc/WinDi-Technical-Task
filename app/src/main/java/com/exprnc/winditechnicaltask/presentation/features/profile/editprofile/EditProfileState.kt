package com.exprnc.winditechnicaltask.presentation.features.profile.editprofile

import com.exprnc.winditechnicaltask.core.ViewState
import com.exprnc.winditechnicaltask.domain.model.User
import java.util.Date

sealed interface EditProfileState : ViewState {
    class DefaultState(
        val name: String,
        val city: String,
        val birthday: Date,
        val showDatePicker: Boolean
    ) : EditProfileState
}