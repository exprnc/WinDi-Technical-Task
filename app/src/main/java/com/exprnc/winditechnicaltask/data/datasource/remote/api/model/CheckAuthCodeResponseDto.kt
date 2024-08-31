package com.exprnc.winditechnicaltask.data.datasource.remote.api.model

import com.google.gson.annotations.SerializedName

data class CheckAuthCodeResponseDto(
    @SerializedName("refresh_token")
    val refreshToken: String?,
    @SerializedName("access_token")
    val accessToken: String?,
    @SerializedName("user_id")
    val userId: Long?,
    @SerializedName("is_user_exists")
    val isUserExists: Boolean?
)