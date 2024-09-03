package com.exprnc.winditechnicaltask.data.repository

import android.content.SharedPreferences
import com.exprnc.winditechnicaltask.data.datasource.remote.api.UserService
import com.exprnc.winditechnicaltask.data.mapper.RefreshTokenMapper
import com.exprnc.winditechnicaltask.domain.repository.TokenRepository
import com.exprnc.winditechnicaltask.utils.orDefault
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val userService: UserService,
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : TokenRepository {

    private val refreshTokenMapper by lazy { RefreshTokenMapper() }

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

    override suspend fun refreshToken(refreshToken: String) = withContext(Dispatchers.IO) {
        val response = userService.refreshToken(refreshToken).apiErrorHandle()
        setToken(response.refreshToken.orDefault(), response.accessToken.orDefault())
        refreshTokenMapper.map(response)
    }
}