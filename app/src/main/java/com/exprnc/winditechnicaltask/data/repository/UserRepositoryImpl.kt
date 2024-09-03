package com.exprnc.winditechnicaltask.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.exprnc.winditechnicaltask.data.datasource.local.room.dao.UserDao
import com.exprnc.winditechnicaltask.data.datasource.remote.api.UserService
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.CheckAuthCodeRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.RegisterRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.SendAuthCodeRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.UserRequestDto
import com.exprnc.winditechnicaltask.data.mapper.AvatarMapper
import com.exprnc.winditechnicaltask.data.mapper.CheckAuthCodeMapper
import com.exprnc.winditechnicaltask.data.mapper.RegisterMapper
import com.exprnc.winditechnicaltask.data.mapper.SendAuthCodeMapper
import com.exprnc.winditechnicaltask.data.mapper.UserEntityToModelMapper
import com.exprnc.winditechnicaltask.data.mapper.UserMapper
import com.exprnc.winditechnicaltask.data.mapper.UserModelToEntityMapper
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.domain.repository.TokenRepository
import com.exprnc.winditechnicaltask.domain.repository.UserRepository
import com.exprnc.winditechnicaltask.utils.orDefault
import com.exprnc.winditechnicaltask.utils.toString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val userDao: UserDao,
    private val tokenRepository: TokenRepository
) : UserRepository {

    private val sendAuthCodeMapper by lazy { SendAuthCodeMapper() }
    private val checkAuthCodeMapper by lazy { CheckAuthCodeMapper() }
    private val registerMapper by lazy { RegisterMapper() }
    private val userMapper by lazy { UserMapper() }
    private val avatarMapper by lazy { AvatarMapper() }
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
        if(response.isUserExists == true) {
            tokenRepository.setToken(response.refreshToken.orDefault(), response.accessToken.orDefault())
        }
        checkAuthCodeMapper.map(response)
    }

    override suspend fun register(phone: String, name: String, username: String) = withContext(Dispatchers.IO) {
        val request = RegisterRequestDto(
            phone = phone,
            name = name,
            userName = username
        )
        val response = userService.register(request).apiErrorHandle()
        tokenRepository.setToken(response.refreshToken.orDefault(), response.accessToken.orDefault())
        registerMapper.map(response)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCurrentUser() = withContext(Dispatchers.IO) {
        val response = userService.getCurrentUser().apiErrorHandle()
        userMapper.map(response)
    }

    // #наговнокодил ._.
    override suspend fun updateUser(
        name: String,
        username: String,
        birthday: Date,
        city: String,
        vk: String,
        instragram: String,
        status: String,
        avatarFileName: String,
        avatarBase64: String
    ) = withContext(Dispatchers.IO) {
        val request = UserRequestDto(
            name = name,
            username = username,
            birthday = birthday.toString("yyyy-MM-dd"),
            city = city,
            vk = vk,
            instagram = instragram,
            status = status,
            avatar = com.exprnc.winditechnicaltask.data.datasource.remote.api.model.Avatar(fileName = avatarFileName, base64 = avatarBase64)
        )
        avatarMapper.map(userService.updateUser(request).apiErrorHandle())
    }

    override suspend fun updateLocalUser(user: User) = withContext(Dispatchers.IO) {
        userDao.updateUser(userModelToEntityMapper.map(user))
    }

    override suspend fun getLocalUser() = withContext(Dispatchers.IO) {
        userEntityToModelMapper.map(userDao.getUser())
    }
}