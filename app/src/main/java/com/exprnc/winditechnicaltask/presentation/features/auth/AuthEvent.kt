package com.exprnc.winditechnicaltask.presentation.features.auth

import com.exprnc.winditechnicaltask.core.ViewEvent

sealed interface AuthEvent : ViewEvent {
    class OpenUrl(val url: String): AuthEvent
}