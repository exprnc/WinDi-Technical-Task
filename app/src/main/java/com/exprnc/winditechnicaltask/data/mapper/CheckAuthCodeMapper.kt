package com.exprnc.winditechnicaltask.data.mapper

import com.exprnc.winditechnicaltask.utils.orDefault
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.CheckAuthCodeResponseDto
import com.exprnc.winditechnicaltask.domain.model.CheckAuthCode

class CheckAuthCodeMapper : Mapper<CheckAuthCodeResponseDto, CheckAuthCode> {
    override fun map(from: CheckAuthCodeResponseDto) =
        CheckAuthCode(
            refreshToken = from.refreshToken.orDefault(),
            accessToken = from.accessToken.orDefault(),
            userId = from.userId.orDefault(),
            isUserExists = from.isUserExists.orDefault()
        )
}