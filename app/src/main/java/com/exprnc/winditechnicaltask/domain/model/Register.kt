package com.exprnc.winditechnicaltask.domain.model

data class Register(
    val refreshToken: String,
    val accessToken: String,
    val userId: Long,
)