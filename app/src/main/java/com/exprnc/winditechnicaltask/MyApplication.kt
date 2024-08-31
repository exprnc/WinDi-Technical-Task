package com.exprnc.winditechnicaltask

import android.app.Application
import com.exprnc.winditechnicaltask.di.AppComponent
import com.exprnc.winditechnicaltask.di.DaggerAppComponent

class MyApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder().application(this).build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}