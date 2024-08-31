package com.exprnc.winditechnicaltask.data.datasource.local.room.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long?,
    val name: String?,
    val username: String?, //
    val birthday: Long?, //
    val city: String?, //
    val vk: String?,
    val instagram: String?,
    val status: String?,
    val avatar: String?, //
    val phone: String?, //
    @Embedded(prefix = "avatars") val avatars: Avatars //
)

data class Avatars(
    val avatar: String?,
    val bigAvatar: String?,
    val miniAvatar: String?
)