package com.exprnc.winditechnicaltask.data.datasource.remote.api.model

import com.google.gson.annotations.SerializedName

data class UserRequestDto(
    val name: String?,
    val username: String?,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: Avatar?
)

data class Avatar(
    @SerializedName("filename")
    val fileName: String?,
    @SerializedName("base_64")
    val base64: String?
)