package com.exprnc.winditechnicaltask.presentation.features.auth

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arpitkatiyarprojects.countrypicker.models.CountryDetails
import com.exprnc.winditechnicaltask.R
import com.exprnc.winditechnicaltask.core.BaseViewModel
import com.exprnc.winditechnicaltask.core.Intent
import com.exprnc.winditechnicaltask.core.ViewEvent
import com.exprnc.winditechnicaltask.domain.model.User.Companion.PHONE_MAX_LENGTH
import com.exprnc.winditechnicaltask.domain.repository.UserRepository
import com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode.VerificationCodeArgs
import com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode.VerificationCodeScreen
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import java.util.Locale

class AuthViewModel @AssistedInject constructor(
    private val application: Application,
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val stateConfigurator = StateConfigurator()

    override val screenResults: (String, Bundle) -> Unit = { key: String, data: Bundle -> }

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
                val phoneMaxLength = PHONE_MAX_LENGTH - stateConfigurator.country.countryPhoneNumberCode.length
                var filteredPhone = intent.field.filter { it.isDigit() }
                if(filteredPhone.length > phoneMaxLength) {
                    emitEvent(ViewEvent.Toast.Text(application.resources.getString(R.string.phone_is_too_long, PHONE_MAX_LENGTH)))
                }
                filteredPhone = filteredPhone.take(phoneMaxLength)
                if(stateConfigurator.phone == filteredPhone) return
                stateConfigurator.phone = filteredPhone
                setState(stateConfigurator.defineFragmentState())
            }
            is Intent.Back -> exit()
            is Intent.OnConfigurationChanged -> setState(stateConfigurator.defineFragmentState())
        }
    }

    // Я бы не стал делать повторный запрос на sendAuthCode, если человек ввёл номер телефона
    // и на экране VerificationCode сделал навигацию назад, а на экране Auth обратно нажал на FAB не меняя номер телефона
    // просто на сервере нету задержки на запросы и я не стал тратить время на это

    private fun sendAuthCode() {
        val currentPhone = stateConfigurator.country.countryPhoneNumberCode + stateConfigurator.phone
        launchCoroutine(needLoader = true) {
            runCatching {
                userRepository.sendAuthCode(currentPhone)
            }.onSuccess {
                if(it.isSuccess) {
                    emitEvent(ViewEvent.Navigation(VerificationCodeScreen(VerificationCodeArgs(currentPhone))))
                } else {
                    emitEvent(ViewEvent.Toast.Id(R.string.send_auth_code_fail_toast))
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