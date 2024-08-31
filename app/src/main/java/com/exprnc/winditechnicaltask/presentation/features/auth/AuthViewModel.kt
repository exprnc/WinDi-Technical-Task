package com.exprnc.winditechnicaltask.presentation.features.auth

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arpitkatiyarprojects.countrypicker.models.CountryDetails
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.BaseViewModel
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.core.ViewEvent
import com.exprnc.winditechnicaltask.domain.repository.UserRepository
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.Locale

class AuthViewModel @AssistedInject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val stateConfigurator = StateConfigurator()

    override val screenResults: (String, Bundle) -> Unit = { key: String, data: Bundle ->
//        when (key) {
//            NewCardScreen.TAG -> {//todo просто как пример, тут должны быть экраны, на которые ты переходишь из этого экрана
//                when (NewCardScreen.getResult(data)) {// а эта обработка на экране откуда вызываешь этот экран NewCardFragment
//                    NewCardScreen.Result.CANCELED -> Unit
//                    NewCardScreen.Result.CARD_CREATED -> Unit
//                }
//            }
//        }
    }

    init {
        setState(stateConfigurator.defineFragmentState())
    }

    override fun obtainIntent(intent: Intent) {
        when (intent) {
            is AuthIntent.OnNextPressed -> sendAuthCode()
            is AuthIntent.OnRegionSelected -> {
                if(stateConfigurator.country == intent.country) return
                stateConfigurator.country = intent.country
                setState(stateConfigurator.defineFragmentState())
            }
            is AuthIntent.OnPhoneInput -> {
                if(stateConfigurator.phone == intent.field) return
                stateConfigurator.phone = intent.field
                setState(stateConfigurator.defineFragmentState())
            }
            is AuthIntent.OnPhoneNumberTooLong -> {
                emitEvent(ViewEvent.Toast.Id(R.string.phone_is_too_long))
            }
            is Intent.Back -> exit()
            is Intent.OnConfigurationChanged -> setState(stateConfigurator.defineFragmentState())
        }
    }

    private fun sendAuthCode() {
        launchCoroutine(needLoader = true) {
            runCatching {
                userRepository.sendAuthCode(stateConfigurator.country.countryPhoneNumberCode + stateConfigurator.phone)
            }.onSuccess {
                println(it)
                if(it.isSuccess) {
//                    emitEvent(ViewEvent.Navigation())
                } else {
                    emitEvent(ViewEvent.Toast.Id(R.string.send_auth_code_fail_toast))
                }
            }.onFailure { e ->
                e.message?.let { emitEvent(ViewEvent.Toast.Text(it)) }
            }
        }
    }

    private fun exit(data: Bundle = AuthScreen.Result.createBundleCanceled()) {
        emitEvent(ViewEvent.PopBackStack(data))
    }

    private inner class StateConfigurator {
        var phone: String = ""
        var country = CountryDetails(
            countryCode = Locale.getDefault().country,
            countryPhoneNumberCode = "",
            countryName = "",
            countryFlag = 0
        )

        fun defineFragmentState(): AuthState {
            return AuthState.DefaultState(phone, country)
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
        fun create(): AuthViewModel
    }
}