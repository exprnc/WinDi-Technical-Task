package com.exprnc.winditechnicaltask.domain.usecase

import com.exprnc.winditechnicaltask.domain.model.Avatars
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.domain.repository.UserRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class UpdateUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        name: String,
        birthday: Date,
        city: String,
        avatarFileName: String,
        avatarBase64: String
    ) : User {
        val localUser = userRepository.getLocalUser()

        val avatar = userRepository.updateUser(
            name = name,
            username = localUser.username,
            birthday = birthday,
            city = city,
            vk = localUser.vk,
            instragram = localUser.instagram,
            status = localUser.status,
            avatarFileName = avatarFileName,
            avatarBase64 = avatarBase64
        )

        val currentUser = User(
            id = localUser.id,
            name = name,
            username = localUser.username,
            birthday = birthday,
            city = city,
            vk = localUser.vk,
            instagram = localUser.instagram,
            status = localUser.status,
            avatar = avatar.avatar,
            phone = localUser.phone,
            avatars = Avatars(avatar = avatar.avatar, bigAvatar = avatar.bigAvatar, miniAvatar = avatar.miniAvatar),
            fileName = avatarFileName,
            base64 = avatarBase64
        )

        userRepository.updateLocalUser(currentUser)
        return currentUser
    }
}