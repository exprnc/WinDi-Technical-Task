package com.exprnc.winditechnicaltask.data.datasource.remote.api.model

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("profile_data")
    val profileData: ProfileData
)

data class ProfileData(
    val id: Long?,
    val name: String?,
    val username: String?,
    val birthday: String?,
    val city: String?,
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: String?,
    val phone: String?,
    val avatars: Avatars?
)

data class Avatars(
    val avatar: String?,
    val bigAvatar: String?,
    val miniAvatar: String?
)