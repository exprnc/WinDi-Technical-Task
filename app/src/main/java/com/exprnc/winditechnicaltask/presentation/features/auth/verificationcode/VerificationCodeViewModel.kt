package com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.BaseViewModel
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.core.ViewEvent
import com.exprnc.winditechnicaltask.domain.repository.UserRepository
import com.exprnc.winditechnicaltask.presentation.features.auth.registration.RegArgs
import com.exprnc.winditechnicaltask.presentation.features.auth.registration.RegScreen
import com.exprnc.winditechnicaltask.presentation.features.chats.ChatsScreen
import com.exprnc.winditechnicaltask.presentation.features.profile.ProfileScreen
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class VerificationCodeViewModel @AssistedInject constructor(
    @Assisted private val args: VerificationCodeArgs,
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
            is VerificationCodeIntent.OnNextPressed -> {
                if(stateConfigurator.code.length != CODE_LENGTH) {
                    emitEvent(ViewEvent.Toast.Text(application.resources.getString(R.string.verification_code_not_filled, CODE_LENGTH)))
                } else {
                    checkAuthCode()
                }
            }

            is VerificationCodeIntent.OnCodeInput -> {
                val filteredCode = intent.field.filter { it.isDigit() }.take(CODE_LENGTH)
                if(stateConfigurator.code == filteredCode) return
                stateConfigurator.code = filteredCode
                setState(stateConfigurator.defineFragmentState())
            }

            is Intent.Back -> exit()

            is Intent.OnConfigurationChanged -> setState(stateConfigurator.defineFragmentState())
        }
    }

    private fun checkAuthCode() {
        launchCoroutine(needLoader = true) {
            runCatching {
                userRepository.checkAuthCode(args.phone, stateConfigurator.code)
            }.onSuccess {
                emitEvent(ViewEvent.PopBackStack())
                if(it.isUserExists) {
                    emitEvent(ViewEvent.Navigation(ChatsScreen()))
                } else {
                    emitEvent(ViewEvent.Navigation(RegScreen(RegArgs(args.phone))))
                }
            }.onFailure { e ->
                e.message?.let { emitEvent(ViewEvent.Toast.Text(it)) }
            }
        }
    }

    private fun exit() {
        emitEvent(ViewEvent.PopBackStack())
    }

    private inner class StateConfigurator {
        var code: String = ""

        fun defineFragmentState(): VerificationCodeState {
            return VerificationCodeState.DefaultState(args.phone ,code)
        }
    }

    companion object {

        const val CODE_LENGTH = 6

        fun provideFactory(
            args: VerificationCodeArgs,
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
            args: VerificationCodeArgs
        ): VerificationCodeViewModel
    }
}