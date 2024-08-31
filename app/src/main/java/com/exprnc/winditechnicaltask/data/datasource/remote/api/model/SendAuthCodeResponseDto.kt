package com.exprnc.winditechnicaltask.data.datasource.remote.api.model

import com.google.gson.annotations.SerializedName

data class SendAuthCodeResponseDto(
    @SerializedName("is_success")
    val isSuccess: Boolean?
)