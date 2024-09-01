package com.exprnc.winditechnicaltask.presentation.features.auth.registration

import com.exprnc.winditechnicaltask.core.Intent

sealed interface RegIntent: Intent {
    data object OnNextPressed : RegIntent
    class OnNameInput(val field: String) : RegIntent
    class OnUsernameInput(val field: String) : RegIntent
}