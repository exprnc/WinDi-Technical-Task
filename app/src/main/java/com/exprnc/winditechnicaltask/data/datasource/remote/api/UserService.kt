package com.exprnc.winditechnicaltask.data.datasource.remote.api

import com.exprnc.winditechnicaltask.core.ApiResponse
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.AvatarResponseDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.CheckAuthCodeRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.CheckAuthCodeResponseDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.RefreshTokenResponseDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.RegisterRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.RegisterResponseDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.SendAuthCodeRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.SendAuthCodeResponseDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.UserRequestDto
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserService {
    @POST("/api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body request: SendAuthCodeRequestDto): ApiResponse<SendAuthCodeResponseDto>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthCode(@Body request: CheckAuthCodeRequestDto) : ApiResponse<CheckAuthCodeResponseDto>

    @POST("/api/v1/users/register/")
    suspend fun register(@Body request: RegisterRequestDto) : ApiResponse<RegisterResponseDto>

    @GET("/api/v1/users/me/")
    suspend fun getCurrentUser() : ApiResponse<UserResponseDto>

    @PUT("/api/v1/users/me/")
    suspend fun updateUser(@Body request: UserRequestDto) : ApiResponse<AvatarResponseDto>

    @POST("/api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body refreshToken: String) : ApiResponse<RefreshTokenResponseDto>
}