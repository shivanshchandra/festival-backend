package com.app.festival_backend.dto.auth

import com.app.festival_backend.entity.User

fun User.toAuthUserData(): AuthUserData {
    return AuthUserData(
        id = this.id,
        username = this.username,
        email = this.email,
        image = this.imageUrl,
        phoneNumber = this.phoneNumber,
        phoneCode = this.phoneCode,
        gender = this.gender
    )
}