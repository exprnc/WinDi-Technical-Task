package com.exprnc.winditechnicaltask.domain.repository

import com.exprnc.winditechnicaltask.domain.model.CheckAuthCode
import com.exprnc.winditechnicaltask.domain.model.Register
import com.exprnc.winditechnicaltask.domain.model.SendAuthCode
import com.exprnc.winditechnicaltask.domain.model.User

interface UserRepository {

    suspend fun sendAuthCode(phone: String): SendAuthCode
    suspend fun checkAuthCode(phone: String, code: String): CheckAuthCode
    suspend fun register(phone: String, name: String, username: String): Register

    suspend fun updateLocalUser(user: User)
    suspend fun getLocalUser(): User
}