package com.exprnc.winditechnicaltask.data.datasource.remote.api.model

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
    val phone: String?,
    val name: String?,
    @SerializedName("username")
    val userName: String?,
)