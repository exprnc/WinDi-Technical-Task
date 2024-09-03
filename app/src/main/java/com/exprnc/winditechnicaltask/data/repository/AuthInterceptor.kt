package com.exprnc.winditechnicaltask.data.repository

import com.exprnc.winditechnicaltask.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptor @Inject constructor(
    private val tokenRepository: Provider<TokenRepository>,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (isAuthExcluded(request.url.encodedPath)) {
            return chain.proceed(request)
        }

        val tokenPair = runBlocking { tokenRepository.get().getToken() }
        val accessToken = tokenPair.second

        val authenticatedRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $accessToken")
            .build()

        val response = chain.proceed(authenticatedRequest)

        if (response.code == 401) {
            response.close()
            val newAccessToken = runBlocking { tokenRepository.get().refreshToken(tokenPair.first) }
            val newRequest = request.newBuilder()
                .addHeader("Authorization", "Bearer $newAccessToken")
                .build()
            return chain.proceed(newRequest)
        }
        return response
    }

    private fun isAuthExcluded(url: String): Boolean {
        return when(url) {
            "/api/v1/users/send-auth-code/" -> true
            "/api/v1/users/check-auth-code/" -> true
            "/api/v1/users/register/" -> true
            else -> false
        }
    }
}