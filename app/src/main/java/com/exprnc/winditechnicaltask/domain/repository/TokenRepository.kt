package com.exprnc.winditechnicaltask.domain.repository

interface TokenRepository {
    suspend fun getToken(): Pair<String, String>
    suspend fun setToken(refreshToken: String, accessToken: String)
}