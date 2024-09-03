package com.exprnc.winditechnicaltask.domain.repository

import com.exprnc.winditechnicaltask.domain.model.RefreshToken

interface TokenRepository {
    suspend fun getToken(): Pair<String, String>
    suspend fun setToken(refreshToken: String, accessToken: String)
    suspend fun refreshToken(refreshToken: String) : RefreshToken
}