package com.app.festival_backend.dto.common

data class ApiResponse<T>(
    val status: Int,
    val message: String,
    val data: T? = null
)