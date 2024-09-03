package com.exprnc.winditechnicaltask.presentation.features.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exprnc.winditechnicaltask.core.BaseViewModel
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.core.ViewEvent
import com.exprnc.winditechnicaltask.domain.model.Avatars
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.domain.usecase.GetUserUseCase
import com.exprnc.winditechnicaltask.domain.usecase.UpdateUserUseCase
import com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode.VerificationCodeArgs
import com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode.VerificationCodeScreen
import com.exprnc.winditechnicaltask.presentation.features.profile.editprofile.EditProfileArgs
import com.exprnc.winditechnicaltask.presentation.features.profile.editprofile.EditProfileScreen
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.Date

class ProfileViewModel @AssistedInject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {

    private val stateConfigurator = StateConfigurator()

    override val screenResults: (String, Bundle) -> Unit = { key: String, data: Bundle ->
        when(key) {
            EditProfileScreen.TAG -> {
                if(EditProfileScreen.getResult(data) == EditProfileScreen.Result.USER_EDITED) {
                    EditProfileScreen.getUser(data)?.let {
                        stateConfigurator.user = it
                        setState(stateConfigurator.defineFragmentState())
                    }
                }
            }
        }
    }

    init {
        getUser()
    }

    override fun obtainIntent(intent: Intent) {
        when(intent) {
            is ProfileIntent.OnEditPressed -> {
                stateConfigurator.expanded = stateConfigurator.expanded.not()
                emitEvent(ViewEvent.Navigation(EditProfileScreen(EditProfileArgs(
                    name = stateConfigurator.user.name,
                    city = stateConfigurator.user.city,
                    birthday = stateConfigurator.user.birthday,
                    fileName = stateConfigurator.user.fileName,
                    base64 = stateConfigurator.user.base64
                ))))
            }
            is ProfileIntent.OnMoreMenuPressed -> {
                stateConfigurator.expanded = stateConfigurator.expanded.not()
                setState(stateConfigurator.defineFragmentState())
            }
            is ProfileIntent.OnAvatarChanged -> {
                if(stateConfigurator.user.fileName == intent.fileName && stateConfigurator.user.base64 == intent.base64) return
                stateConfigurator.user.fileName = intent.fileName
                stateConfigurator.user.base64 = intent.base64
                updateUserAvatar()
            }
            is Intent.Back -> exit()
            is Intent.OnConfigurationChanged -> setState(stateConfigurator.defineFragmentState())
        }
    }

    private fun updateUserAvatar() {
        launchCoroutine(true) {
            runCatching {
                updateUserUseCase.invoke(
                    name = stateConfigurator.user.name,
                    birthday = stateConfigurator.user.birthday,
                    city = stateConfigurator.user.city,
                    avatarFileName = stateConfigurator.user.fileName,
                    avatarBase64 = stateConfigurator.user.base64
                )
            }.onSuccess {
                stateConfigurator.user = it
                setState(stateConfigurator.defineFragmentState())
            }.onFailure { e ->
                e.printStackTrace()
                e.message?.let { emitEvent(ViewEvent.Toast.Text(it)) }
            }
        }
    }

    private fun getUser() {
        launchCoroutine(true) {
            runCatching {
                getUserUseCase.invoke()
            }.onSuccess {
                stateConfigurator.user = it
                setState(stateConfigurator.defineFragmentState())
            }.onFailure { e ->
                e.printStackTrace()
                e.message?.let { emitEvent(ViewEvent.Toast.Text(it)) }
            }
        }
    }

    private fun exit() {
        emitEvent(ViewEvent.PopBackStack())
    }

    private inner class StateConfigurator {
        var user = User(
            id = 0,
            name = "",
            username = "",
            birthday = Date(0),
            city = "",
            vk = "",
            instagram = "",
            status = "",
            avatar = "",
            phone = "",
            avatars = Avatars(avatar = "", bigAvatar = "", miniAvatar = ""),
            fileName = "",
            base64 = ""
        )
        var expanded = false

        fun defineFragmentState(): ProfileState {
            return ProfileState.DefaultState(user, expanded)
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory
        ): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {

                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistedFactory.create() as T
                }
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(): ProfileViewModel
    }
}