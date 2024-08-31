package com.exprnc.winditechnicaltask.domain.model

import java.util.Date

data class User(
    val id: Long,
    val name: String,
    val username: String,
    val birthday: Date,
    val city: String,
    val vk: String,
    val instagram: String,
    val status: String,
    val avatar: String,
    val phone: String,
    val avatars: Avatars
)

data class Avatars(
    val avatar: String,
    val bigAvatar: String,
    val miniAvatar: String
)