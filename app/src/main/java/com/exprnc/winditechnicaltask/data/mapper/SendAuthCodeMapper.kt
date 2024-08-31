package com.exprnc.winditechnicaltask.data.mapper

import com.exprnc.winditechnicaltask.utils.orDefault
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.SendAuthCodeResponseDto
import com.exprnc.winditechnicaltask.data.mapper.Mapper
import com.exprnc.winditechnicaltask.domain.model.SendAuthCode

class SendAuthCodeMapper : Mapper<SendAuthCodeResponseDto, SendAuthCode> {
    override fun map(from: SendAuthCodeResponseDto) =
        SendAuthCode(
            isSuccess = from.isSuccess.orDefault()
        )
}