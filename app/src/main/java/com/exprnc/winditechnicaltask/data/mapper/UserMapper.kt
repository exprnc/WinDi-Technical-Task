package com.exprnc.winditechnicaltask.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.exprnc.winditechnicaltask.data.datasource.remote.api.model.UserResponseDto
import com.exprnc.winditechnicaltask.domain.model.Avatars
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.utils.orDefault
import java.time.Instant
import java.util.Date

class UserMapper : Mapper<UserResponseDto, User> {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun map(from: UserResponseDto) : User {
        val userResponseDto = from.profileData
        val userBirthdayDate = parseDateOrNull(userResponseDto.birthday.orDefault()) ?: Date(0)
        val userPhone = if(userResponseDto.phone == null) {
            ""
        } else {
            "+" + userResponseDto.phone
        }

        return User(
            id = userResponseDto.id.orDefault(),
            name = userResponseDto.name.orDefault(),
            username = userResponseDto.username.orDefault(),
            birthday = userBirthdayDate,
            city = userResponseDto.city.orDefault(),
            vk = userResponseDto.vk.orDefault(),
            instagram = userResponseDto.instagram.orDefault(),
            status = userResponseDto.status.orDefault(),
            avatar = userResponseDto.avatar.orDefault(),
            phone = userPhone,
            avatars = Avatars(
                avatar = userResponseDto.avatars?.avatar.orDefault(),
                bigAvatar = userResponseDto.avatars?.bigAvatar.orDefault(),
                miniAvatar = userResponseDto.avatars?.miniAvatar.orDefault()
            ),
            fileName = "",
            base64 = ""
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun parseDateOrNull(dateString: String?): Date? {
        return try {
            if (dateString.isNullOrEmpty()) {
                null
            } else {
                val instant = Instant.parse(dateString)
                Date.from(instant)
            }
        } catch (e: Exception) {
            null
        }
    }
}