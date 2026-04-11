package com.app.festival_backend.dto.image

data class ImagePostRequest(
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String? = null,
    val categoryId: Long,
    val isPremium: Boolean = false,
    val active: Boolean = true,
    val displayOrder: Int = 0
)