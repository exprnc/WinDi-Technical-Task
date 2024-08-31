package com.exprnc.winditechnicaltask.di

import android.app.Application
import com.exprnc.winditechnicaltask.presentation.features.auth.AuthFragment
import com.exprnc.winditechnicaltask.presentation.features.auth.AuthViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(authFragment: AuthFragment)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}