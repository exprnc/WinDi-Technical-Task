package com.exprnc.winditechnicaltask.data.mapper

import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.RefreshTokenResponseDto
import com.exprnc.winditechnicaltask.domain.model.RefreshToken
import com.exprnc.winditechnicaltask.utils.orDefault

class RefreshTokenMapper : Mapper<RefreshTokenResponseDto, RefreshToken> {
    override fun map(from: RefreshTokenResponseDto) =
        RefreshToken(
            refreshToken = from.refreshToken.orDefault(),
            accessToken = from.accessToken.orDefault(),
            userId = from.userId.orDefault()
        )
}