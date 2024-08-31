package com.exprnc.winditechnicaltask.data.datasource.remote.api

import com.exprnc.winditechnicaltask.core.ApiResponse
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.CheckAuthCodeRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.CheckAuthCodeResponseDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.RegisterRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.RegisterResponseDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.SendAuthCodeRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.SendAuthCodeResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body request: SendAuthCodeRequestDto): ApiResponse<SendAuthCodeResponseDto>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: CheckAuthCodeRequestDto) : ApiResponse<CheckAuthCodeResponseDto>

    @POST("/api/v1/users/register/")
    suspend fun register(@Body request: RegisterRequestDto) : ApiResponse<RegisterResponseDto>
}