package com.exprnc.winditechnicaltask.data.repository

import android.content.SharedPreferences
import com.exprnc.winditechnicaltask.data.datasource.local.room.dao.UserDao
import com.exprnc.winditechnicaltask.data.datasource.remote.api.UserService
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.CheckAuthCodeRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.RegisterRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.SendAuthCodeRequestDto
import com.exprnc.winditechnicaltask.data.mapper.CheckAuthCodeMapper
import com.exprnc.winditechnicaltask.data.mapper.RegisterMapper
import com.exprnc.winditechnicaltask.data.mapper.SendAuthCodeMapper
import com.exprnc.winditechnicaltask.data.mapper.UserEntityToModelMapper
import com.exprnc.winditechnicaltask.data.mapper.UserModelToEntityMapper
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.domain.repository.UserRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : UserRepository {

    private val sendAuthCodeMapper by lazy { SendAuthCodeMapper() }
    private val checkAuthCodeMapper by lazy { CheckAuthCodeMapper() }
    private val registerMapper by lazy { RegisterMapper() }
    private val userEntityToModelMapper by lazy { UserEntityToModelMapper() }
    private val userModelToEntityMapper by lazy { UserModelToEntityMapper() }

    override suspend fun sendAuthCode(phone: String) = withContext(Dispatchers.IO) {
        val request = SendAuthCodeRequestDto(phone = phone)
        val response = userService.sendAuthCode(request).apiErrorHandle()
        sendAuthCodeMapper.map(response)
    }

    override suspend fun checkAuthCode(phone: String, code: String) = withContext(Dispatchers.IO) {
        val request = CheckAuthCodeRequestDto(phone = phone, code = code)
        val response = userService.checkAuthCode(request).apiErrorHandle()
        checkAuthCodeMapper.map(response)
    }

    override suspend fun register(phone: String, name: String, username: String) = withContext(Dispatchers.IO) {
        val request = RegisterRequestDto(
            phone = phone,
            name = name,
            userName = username
        )
        val response = userService.register(request).apiErrorHandle()
        registerMapper.map(response)
    }

    override suspend fun updateLocalUser(user: User) = withContext(Dispatchers.IO) {
        userDao.updateUser(userModelToEntityMapper.map(user))
    }

    override suspend fun getLocalUser() = withContext(Dispatchers.IO) {
        userEntityToModelMapper.map(userDao.getUser())
    }

    override suspend fun getToken() = withContext(Dispatchers.IO) {
        val tokenJson = sharedPreferences.getString("TOKEN_KEY", null)
        if (tokenJson != null) {
            val type = object : TypeToken<Pair<String, String>>() {}.type
            gson.fromJson(tokenJson, type)
        } else {
            Pair("", "")
        }
    }

    override suspend fun setToken(refreshToken: String, accessToken: String) = withContext(Dispatchers.IO) {
        val token = Pair(refreshToken, accessToken)
        val tokenJson = gson.toJson(token)
        sharedPreferences.edit().putString("TOKEN_KEY", tokenJson).apply()
    }
}