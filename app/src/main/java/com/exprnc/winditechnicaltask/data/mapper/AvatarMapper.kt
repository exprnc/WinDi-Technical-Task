package com.exprnc.winditechnicaltask.data.mapper

import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.AvatarResponseDto
import com.exprnc.winditechnicaltask.domain.model.Avatar
import com.exprnc.winditechnicaltask.utils.orDefault

class AvatarMapper : Mapper<AvatarResponseDto, Avatar> {
    override fun map(from: AvatarResponseDto) = 
        Avatar(
            avatar = from.avatars?.avatar.orDefault(),
            bigAvatar = from.avatars?.bigAvatar.orDefault(),
            miniAvatar = from.avatars?.miniAvatar.orDefault()
        )
}