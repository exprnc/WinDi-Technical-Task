package com.exprnc.winditechnicaltask.domain.model

data class CheckAuthCode(
    val refreshToken: String,
    val accessToken: String,
    val userId: Long,
    val isUserExists: Boolean
)