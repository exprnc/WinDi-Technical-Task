package com.exprnc.winditechnicaltask.domain.model

data class RefreshToken(
    val refreshToken: String,
    val accessToken: String,
    val userId: Long,
)
