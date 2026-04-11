package com.app.festival_backend.dto.auth

import com.app.festival_backend.entity.Role

data class AuthUserData(
    val id: Long,
    val username: String,
    val email: String,
    val role: Role,
    val image: String? = null,
    val phoneNumber: String? = null,
    val phoneCode: String? = null,
    val gender: String? = null,
    val token: String? = null
)