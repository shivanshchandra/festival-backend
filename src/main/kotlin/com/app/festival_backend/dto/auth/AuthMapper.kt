package com.app.festival_backend.dto.auth

import com.app.festival_backend.entity.User

fun User.toAuthUserData(token: String? = null): AuthUserData {
    return AuthUserData(
        id = this.id,
        username = this.username,
        email = this.email,
        role = this.role,
        image = this.imageUrl,
        phoneNumber = this.phoneNumber,
        phoneCode = this.phoneCode,
        gender = this.gender,
        token = token
    )
}