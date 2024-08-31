package com.exprnc.winditechnicaltask.presentation.features.auth

import com.arpitkatiyarprojects.countrypicker.models.CountryDetails
import com.exprnc.winditechnicaltask.core.Intent

sealed interface AuthIntent: Intent {
    data object OnNextPressed : AuthIntent
    class OnPhoneInput(val field: String) : AuthIntent
    data object OnPhoneNumberTooLong : AuthIntent
    class OnRegionSelected(val country: CountryDetails) : AuthIntent
}