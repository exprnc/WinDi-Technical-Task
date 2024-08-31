package com.exprnc.winditechnicaltask.data.mapper

import com.exprnc.winditechnicaltask.utils.orDefault
import com.exprnc.winditechnicaltask.data.datasource.local.room.entities.UserEntity
import com.exprnc.winditechnicaltask.data.mapper.Mapper
import com.exprnc.winditechnicaltask.domain.model.Avatars
import com.exprnc.winditechnicaltask.domain.model.User
import java.util.Date

class UserEntityToModelMapper : Mapper<UserEntity, User> {
    override fun map(from: UserEntity) =
        User(
            id = from.id.orDefault(),
            name = from.name.orDefault(),
            username = from.username.orDefault(),
            birthday = Date(from.birthday.orDefault()),
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
            )
        )
}