package com.exprnc.winditechnicaltask.data.mapper

import com.exprnc.winditechnicaltask.data.datasource.local.room.entities.Avatars
import com.exprnc.winditechnicaltask.data.datasource.local.room.entities.UserEntity
import com.exprnc.winditechnicaltask.data.mapper.Mapper
import com.exprnc.winditechnicaltask.domain.model.User

class UserModelToEntityMapper : Mapper<User, UserEntity> {
    override fun map(from: User) =
        UserEntity(
            id = from.id,
            name = from.name,
            username = from.username,
            birthday = from.birthday.time,
            city = from.city,
            vk = from.vk,
            instagram = from.instagram,
            status = from.status,
            avatar = from.avatar,
            phone = from.phone,
            avatars = Avatars(
                avatar = from.avatars.avatar,
                bigAvatar = from.avatars.bigAvatar,
                miniAvatar = from.avatars.miniAvatar
            )
        )
}