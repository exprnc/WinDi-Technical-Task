package com.exprnc.winditechnicaltask.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.exprnc.winditechnicaltask.data.datasource.local.room.dao.UserDao
import com.exprnc.winditechnicaltask.data.datasource.local.room.database.AppDatabase
import com.exprnc.winditechnicaltask.data.datasource.remote.api.UserService
import com.exprnc.winditechnicaltask.data.datasource.remote.api.adapter.ApiResponseTypeAdapterFactory
import com.exprnc.winditechnicaltask.data.repository.TokenRepositoryImpl
import com.exprnc.winditechnicaltask.data.repository.UserRepositoryImpl
import com.exprnc.winditechnicaltask.domain.repository.TokenRepository
import com.exprnc.winditechnicaltask.domain.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
interface AppModule {

    @Binds
    @Singleton
    fun bindUserRepository(
        impl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    @Singleton
    fun bindTokenRepository(
        impl: TokenRepositoryImpl,
    ): TokenRepository

    companion object {

        @Singleton
        @Provides
        fun provideGson(): Gson {
            return GsonBuilder()
                .registerTypeAdapterFactory(ApiResponseTypeAdapterFactory())
                .create()
        }

        @Singleton
        @Provides
        fun provideDatabase(application: Application): AppDatabase {
            return Room.databaseBuilder(
                application,
                AppDatabase::class.java,
                "app_database"
            ).build()
        }

        @Singleton
        @Provides
        fun provideUserDao(database: AppDatabase): UserDao {
            return database.userDao()
        }

        @Singleton
        @Provides
        fun provideEncryptedSharedPreferences(application: Application): SharedPreferences {
            val masterKey = MasterKey.Builder(application)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            return EncryptedSharedPreferences.create(
                application,
                "secret_shared_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        @Singleton
        @Provides
        fun provideUserService(gson: Gson): UserService {
            return Retrofit.Builder()
                .baseUrl("https://plannerok.ru")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(UserService::class.java)
        }
    }
}