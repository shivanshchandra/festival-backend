package com.app.festival_backend.dto.auth

import com.app.festival_backend.entity.Role
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class UpdateUserRequest(

    @field:NotNull(message = "User id is required")
    val userId: Long,

    @field:Size(min = 2, max = 100, message = "Username must be between 2 and 100 characters")
    val username: String? = null,

    @field:Email(message = "Email must be valid")
    val email: String? = null,

    @field:Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    val password: String? = null,

    val role: Role? = null,

    val image: String? = null,

    val phoneNumber: String? = null,

    val phoneCode: String? = null,

    val gender: String? = null
)