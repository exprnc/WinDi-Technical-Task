package com.exprnc.winditechnicaltask.presentation.features.profile.editprofile

import com.exprnc.winditechnicaltask.core.Intent
import java.util.Date

sealed interface EditProfileIntent : Intent {
    data object OnConfirmPressed : EditProfileIntent
    class OnNameInput(val field: String) : EditProfileIntent
    class OnCityInput(val field: String) : EditProfileIntent
    class OnBirthdaySelected(val birthday: Date) : EditProfileIntent
    data object OnDatePickerPressed : EditProfileIntent
}