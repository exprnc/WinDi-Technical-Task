package com.exprnc.winditechnicaltask.domain.usecase

import arrow.core.Either
import com.exprnc.winditechnicaltask.domain.model.User
import com.exprnc.winditechnicaltask.domain.repository.TokenRepository
import com.exprnc.winditechnicaltask.domain.repository.UserRepository
import io.github.nefilim.kjwt.JWT
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): User {
        val decodedToken = JWT.decode(tokenRepository.getToken().second)
        val decodedPhone = if (decodedToken is Either.Right) {
            val phoneOption = decodedToken.value.claimValue("phone")
            phoneOption.fold({ "" }, { it })
        } else { "" }
        val localUser = userRepository.getLocalUser()
        val filteredLocalPhone = localUser.phone.filterNot { it == '+' }
        if(localUser.phone.isEmpty() || filteredLocalPhone != decodedPhone) {
            val currentUser = userRepository.getCurrentUser()
            userRepository.updateLocalUser(currentUser)
            return currentUser
        }
        return localUser
    }
}