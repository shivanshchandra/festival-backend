package com.app.festival_backend.dto.video

data class VideoPostRequest(
    val title: String,
    val videoUrl: String,
    val thumbnailUrl: String? = null,
    val categoryId: Long,
    val isPremium: Boolean = false,
    val active: Boolean = true,
    val displayOrder: Int = 0
)