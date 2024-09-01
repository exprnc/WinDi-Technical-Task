package com.exprnc.winditechnicaltask.data.repository

import android.content.SharedPreferences
import com.exprnc.winditechnicaltask.domain.repository.TokenRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : TokenRepository {

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