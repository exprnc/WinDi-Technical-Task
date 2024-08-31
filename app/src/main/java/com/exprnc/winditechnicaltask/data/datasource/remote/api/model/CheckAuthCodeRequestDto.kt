package com.exprnc.winditechnicaltask.data.datasource.remote.api.model

data class CheckAuthCodeRequestDto(
    val phone: String,
    val code: String
)