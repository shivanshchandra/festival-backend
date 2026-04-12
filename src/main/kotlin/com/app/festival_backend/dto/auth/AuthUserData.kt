package com.app.festival_backend.dto.auth

data class AuthUserData(
    val id: Long,
    val username: String,
    val email: String,
    val image: String? = null,
    val phoneNumber: String? = null,
    val phoneCode: String? = null,
    val gender: String? = null
)