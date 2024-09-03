package com.exprnc.winditechnicaltask.presentation.features.profile

import com.exprnc.winditechnicaltask.core.Intent

sealed interface ProfileIntent : Intent {
    data object OnMoreMenuPressed : ProfileIntent
    data object OnEditPressed : ProfileIntent
    data class OnAvatarChanged(val fileName: String, val base64: String) : ProfileIntent
}