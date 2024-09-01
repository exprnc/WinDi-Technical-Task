package com.exprnc.winditechnicaltask.presentation.features.auth.registration

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.BaseViewModel
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.core.ViewEvent
import com.exprnc.winditechnicaltask.domain.repository.UserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class RegViewModel @AssistedInject constructor(
    @Assisted private val args: RegArgs,
    private val userRepository: UserRepository,
    private val application: Application
) : BaseViewModel() {
    private val stateConfigurator = StateConfigurator()

    override val screenResults: (String, Bundle) -> Unit = { key: String, data: Bundle -> }

    init {
        setState(stateConfigurator.defineFragmentState())
    }

    override fun obtainIntent(intent: Intent) {
        when(intent) {
            is RegIntent.OnNextPressed -> {
                if(stateConfigurator.username.length < USERNAME_MIN_LENGTH) {
                    emitEvent(ViewEvent.Toast.Text(application.resources.getString(R.string.username_is_too_short, USERNAME_MIN_LENGTH)))
                }else{
                    register()
                }
            }

            is RegIntent.OnNameInput -> {
                if(stateConfigurator.name.length > NAME_MAX_LENGTH) {
                    emitEvent(ViewEvent.Toast.Text(application.resources.getString(R.string.name_is_too_long, NAME_MAX_LENGTH)))
                }
                stateConfigurator.name = stateConfigurator.name.take(NAME_MAX_LENGTH)
                if(stateConfigurator.name == intent.field) return
                stateConfigurator.name = intent.field
                setState(stateConfigurator.defineFragmentState())
            }

            is RegIntent.OnUsernameInput -> {
                if(intent.field.any { it !in USERNAME_ALLOWED_CHARS }) {
                    emitEvent(ViewEvent.Toast.Id(R.string.username_has_invalid_chars))
                }
                var filteredUsername = intent.field.filter { it in USERNAME_ALLOWED_CHARS }
                if(filteredUsername.length > USERNAME_MAX_LENGTH) {
                    emitEvent(ViewEvent.Toast.Text(application.resources.getString(R.string.username_is_too_long, USERNAME_MAX_LENGTH)))
                }
                filteredUsername = filteredUsername.take(USERNAME_MAX_LENGTH)
                if(stateConfigurator.username == filteredUsername) return
                stateConfigurator.username = filteredUsername
                setState(stateConfigurator.defineFragmentState())
            }

            is Intent.Back -> exit()
            is Intent.OnConfigurationChanged -> setState(stateConfigurator.defineFragmentState())
        }
    }

    private fun register() {
        launchCoroutine(needLoader = true) {
            runCatching {
                userRepository.register(phone = args.phone, name = stateConfigurator.name, username = stateConfigurator.username)
            }.onSuccess {
                println(it)
//                emitEvent(ViewEvent.PopBackStack())
//                emitEvent(ViewEvent.Navigation())  Навигация на экран чатов
            }.onFailure { e ->
                e.message?.let { emitEvent(ViewEvent.Toast.Text(it)) }
            }
        }
    }

    private fun exit() {
        emitEvent(ViewEvent.PopBackStack())
    }

    private inner class StateConfigurator {
        var name: String = ""
        var username: String = ""

        fun defineFragmentState(): RegState {
            return RegState.DefaultState(phone = args.phone, name = name, username = username)
        }
    }

    companion object {

        private const val NAME_MAX_LENGTH = 128
        private const val USERNAME_MAX_LENGTH = 32
        private const val USERNAME_MIN_LENGTH = 5
        private const val USERNAME_ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"

        fun provideFactory(
            args: RegArgs,
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
            args: RegArgs
        ): RegViewModel
    }
}