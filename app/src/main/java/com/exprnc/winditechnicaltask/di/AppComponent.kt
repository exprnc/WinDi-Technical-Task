package com.exprnc.winditechnicaltask.di

import android.app.Application
import com.exprnc.winditechnicaltask.presentation.features.auth.AuthFragment
import com.exprnc.winditechnicaltask.presentation.features.auth.AuthViewModel
import com.exprnc.winditechnicaltask.presentation.features.auth.registration.RegFragment
import com.exprnc.winditechnicaltask.presentation.features.auth.verificationcode.VerificationCodeFragment
import com.exprnc.winditechnicaltask.presentation.features.profile.ProfileFragment
import com.exprnc.winditechnicaltask.presentation.features.profile.editprofile.EditProfileFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(authFragment: AuthFragment)
    fun inject(verificationCodeFragment: VerificationCodeFragment)
    fun inject(regFragment: RegFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(editProfileFragment: EditProfileFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}