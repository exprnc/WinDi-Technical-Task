package com.exprnc.winditechnicaltask.presentation.features.profile.editprofile

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.BaseViewModel
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.core.ViewEvent
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.domain.model.User.Companion
import com.exprnc.winditechnicaltask.domain.usecase.GetUserUseCase
import com.exprnc.winditechnicaltask.domain.usecase.UpdateUserUseCase
import com.exprnc.winditechnicaltask.presentation.features.auth.registration.RegArgs
import com.exprnc.winditechnicaltask.presentation.features.auth.registration.RegState
import com.exprnc.winditechnicaltask.presentation.features.auth.registration.RegViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.Date

class EditProfileViewModel @AssistedInject constructor(
    @Assisted private val args: EditProfileArgs,
    private val updateUserUseCase: UpdateUserUseCase,
    private val application: Application
) : BaseViewModel() {

    private val stateConfigurator = StateConfigurator()

    override val screenResults: (String, Bundle) -> Unit = { key: String, data: Bundle -> }

    init {
        setState(stateConfigurator.defineFragmentState())
    }

    override fun obtainIntent(intent: Intent) {
        when(intent) {
            is EditProfileIntent.OnNameInput -> {
                if(stateConfigurator.name.length > User.NAME_MAX_LENGTH) {
                    emitEvent(ViewEvent.Toast.Text(application.resources.getString(R.string.name_is_too_long, User.NAME_MAX_LENGTH)))
                }
                stateConfigurator.name = stateConfigurator.name.take(User.NAME_MAX_LENGTH)
                if(stateConfigurator.name == intent.field) return
                stateConfigurator.name = intent.field
                setState(stateConfigurator.defineFragmentState())
            }
            is EditProfileIntent.OnCityInput -> {
                if(stateConfigurator.city == intent.field) return
                stateConfigurator.city = intent.field
                setState(stateConfigurator.defineFragmentState())
            }
            is EditProfileIntent.OnBirthdaySelected -> {
                stateConfigurator.showDatePicker = stateConfigurator.showDatePicker.not()
                stateConfigurator.birthday = intent.birthday
                setState(stateConfigurator.defineFragmentState())
            }
            is EditProfileIntent.OnDatePickerPressed -> {
                stateConfigurator.showDatePicker = stateConfigurator.showDatePicker.not()
                setState(stateConfigurator.defineFragmentState())
            }
            is EditProfileIntent.OnConfirmPressed -> {
                if(stateConfigurator.name == args.name && stateConfigurator.city == args.city && stateConfigurator.birthday == args.birthday) {
                    emitEvent(ViewEvent.PopBackStack())
                    return
                }
                updateUser()
            }
            is Intent.Back -> exit()
            is Intent.OnConfigurationChanged -> setState(stateConfigurator.defineFragmentState())
        }
    }

    private fun updateUser() {
        launchCoroutine(true) {
            runCatching {
                updateUserUseCase.invoke(
                    name = stateConfigurator.name,
                    birthday = stateConfigurator.birthday,
                    city = stateConfigurator.city,
                    avatarFileName = args.fileName,
                    avatarBase64 = args.base64
                )
            }.onSuccess {
                exit(EditProfileScreen.Result.createBundleUserEdited(it))
            }.onFailure { e ->
                e.printStackTrace()
                e.message?.let { emitEvent(ViewEvent.Toast.Text(it)) }
            }
        }
    }

    private fun exit(result: Bundle = EditProfileScreen.Result.createBundleCanceled()) {
        emitEvent(ViewEvent.PopBackStack(result))
    }

    private inner class StateConfigurator {
        var name = args.name
        var city = args.city
        var birthday = args.birthday
        var showDatePicker = false

        fun defineFragmentState(): EditProfileState {
            return EditProfileState.DefaultState(name = name, city = city, birthday = birthday, showDatePicker)
        }
    }

    companion object {
        fun provideFactory(
            args: EditProfileArgs,
            assistedFactory: Factory
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create(
                        args
                    ) as T
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            args: EditProfileArgs
        ): EditProfileViewModel
    }
}