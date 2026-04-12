package com.app.festival_backend.service

import com.app.festival_backend.dto.auth.AuthUserData
import com.app.festival_backend.dto.auth.RegisterRequest
import com.app.festival_backend.dto.auth.UpdateUserRequest
import com.app.festival_backend.dto.auth.toAuthUserData
import com.app.festival_backend.entity.User
import com.app.festival_backend.exception.BadRequestException
import com.app.festival_backend.exception.ResourceNotFoundException
import com.app.festival_backend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository
) {

    fun register(request: RegisterRequest): AuthUserData {
        val normalizedEmail = request.email.trim().lowercase()

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw BadRequestException("Email is already registered")
        }

        val user = User(
            username = request.username.trim(),
            email = normalizedEmail,
            password = request.password
        )

        val savedUser = userRepository.save(user)
        return savedUser.toAuthUserData()
    }

    fun updateUser(request: UpdateUserRequest): AuthUserData {
        val user = userRepository.findById(request.userId)
            .orElseThrow { ResourceNotFoundException("User not found with id: ${request.userId}") }

        request.username?.let { user.username = it.trim() }

        request.email?.let {
            val normalizedEmail = it.trim().lowercase()

            if (normalizedEmail != user.email && userRepository.existsByEmail(normalizedEmail)) {
                throw BadRequestException("Email is already registered")
            }

            user.email = normalizedEmail
        }

        request.password?.let {
            user.password = it
        }

        request.image?.let { user.imageUrl = it }
        request.phoneNumber?.let { user.phoneNumber = it }
        request.phoneCode?.let { user.phoneCode = it }
        request.gender?.let { user.gender = it }

        val updatedUser = userRepository.save(user)
        return updatedUser.toAuthUserData()
    }
}