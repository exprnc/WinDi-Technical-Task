package com.exprnc.winditechnicaltask.presentation.features.auth

import com.arpitkatiyarprojects.countrypicker.models.CountryDetails
import com.exprnc.winditechnicaltask.core.ViewState

sealed interface AuthState : ViewState {
    class DefaultState(val phone: String, val country: CountryDetails) : AuthState
}