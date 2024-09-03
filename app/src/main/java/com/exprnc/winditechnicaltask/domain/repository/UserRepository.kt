package com.exprnc.winditechnicaltask.domain.repository

import com.exprnc.winditechnicaltask.domain.model.Avatar
import com.exprnc.winditechnicaltask.domain.model.CheckAuthCode
import com.exprnc.winditechnicaltask.domain.model.RefreshToken
import com.exprnc.winditechnicaltask.domain.model.Register
import com.exprnc.winditechnicaltask.domain.model.SendAuthCode
import com.exprnc.winditechnicaltask.domain.model.User
import java.util.Date

interface UserRepository {

    suspend fun sendAuthCode(phone: String): SendAuthCode
    suspend fun checkAuthCode(phone: String, code: String): CheckAuthCode
    suspend fun register(phone: String, name: String, username: String): Register
    suspend fun getCurrentUser() : User
    suspend fun updateUser(
        name: String,
        username: String,
        birthday: Date,
        city: String,
        vk: String,
        instragram: String,
        status: String,
        avatarFileName: String,
        avatarBase64: String) : Avatar

    suspend fun updateLocalUser(user: User)
    suspend fun getLocalUser(): User
}