package com.exprnc.winditechnicaltask.data.mapper

import com.exprnc.winditechnicaltask.utils.orDefault
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.RegisterResponseDto
import com.exprnc.winditechnicaltask.data.mapper.Mapper
import com.exprnc.winditechnicaltask.domain.model.Register

class RegisterMapper : Mapper<RegisterResponseDto, Register> {
    override fun map(from: RegisterResponseDto) =
        Register(
            refreshToken = from.refreshToken.orDefault(),
            accessToken = from.accessToken.orDefault(),
            userId = from.userId.orDefault()
        )
}