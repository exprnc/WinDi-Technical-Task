package com.exprnc.winditechnicaltask.data.mapper

import com.exprnc.winditechnicaltask.data.datasource.local.room.entities.Avatars
import com.exprnc.winditechnicaltask.data.datasource.local.room.entities.UserEntity
import com.exprnc.winditechnicaltask.data.mapper.Mapper
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.utils.orDefault

class UserModelToEntityMapper : Mapper<User, UserEntity> {
    override fun map(from: User) =
        UserEntity(
            id = from.id.orDefault(),
            name = from.name.orDefault(),
            username = from.username.orDefault(),
            birthday = from.birthday.time.orDefault(),
            city = from.city.orDefault(),
            vk = from.vk.orDefault(),
            instagram = from.instagram.orDefault(),
            status = from.status.orDefault(),
            avatar = from.avatar.orDefault(),
            phone = from.phone.orDefault(),
            avatars = Avatars(
                avatar = from.avatars.avatar.orDefault(),
                bigAvatar = from.avatars.bigAvatar.orDefault(),
                miniAvatar = from.avatars.miniAvatar.orDefault()
            ),
            fileName = from.fileName.orDefault(),
            base64 = from.base64.orDefault(),
        )
}