package com.exprnc.winditechnicaltask.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
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
    val avatars: Avatars,
    var fileName: String,
    var base64: String
) : Parcelable {
    companion object {
        const val PHONE_MAX_LENGTH = 30
        const val NAME_MAX_LENGTH = 128
        const val USERNAME_MAX_LENGTH = 32
        const val USERNAME_MIN_LENGTH = 5
        const val USERNAME_ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
    }
}

@Parcelize
data class Avatars(
    val avatar: String,
    val bigAvatar: String,
    val miniAvatar: String
): Parcelable