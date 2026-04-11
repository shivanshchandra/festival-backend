package com.app.festival_backend.service

import com.app.festival_backend.config.JwtService
import com.app.festival_backend.dto.auth.AuthUserData
import com.app.festival_backend.dto.auth.LoginRequest
import com.app.festival_backend.dto.auth.RegisterRequest
import com.app.festival_backend.dto.auth.UpdateUserRequest
import com.app.festival_backend.dto.auth.toAuthUserData
import com.app.festival_backend.entity.User
import com.app.festival_backend.exception.BadRequestException
import com.app.festival_backend.exception.ResourceNotFoundException
import com.app.festival_backend.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    fun register(request: RegisterRequest): AuthUserData {
        val normalizedEmail = request.email.trim().lowercase()

        if (userRepository.existsByEmail(normalizedEmail)) {
            throw BadRequestException("Email is already registered")
        }

        val user = User(
            username = request.username.trim(),
            email = normalizedEmail,
            password = passwordEncoder.encode(request.password),
            role = request.role
        )

        val savedUser = userRepository.save(user)
        return savedUser.toAuthUserData()
    }

    fun login(request: LoginRequest): AuthUserData {
        val normalizedEmail = request.email.trim().lowercase()

        val user = userRepository.findByEmail(normalizedEmail)
            .orElseThrow { BadRequestException("Invalid email or password") }

        val passwordMatches = passwordEncoder.matches(request.password, user.password)
        if (!passwordMatches) {
            throw BadRequestException("Invalid email or password")
        }

        val token = jwtService.generateToken(user.email)
        return user.toAuthUserData(token)
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
            user.password = passwordEncoder.encode(it)
        }

        request.role?.let { user.role = it }
        request.image?.let { user.imageUrl = it }
        request.phoneNumber?.let { user.phoneNumber = it }
        request.phoneCode?.let { user.phoneCode = it }
        request.gender?.let { user.gender = it }

        val updatedUser = userRepository.save(user)
        return updatedUser.toAuthUserData()
    }
}