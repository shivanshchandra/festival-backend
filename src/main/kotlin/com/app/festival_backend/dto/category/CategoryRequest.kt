package com.app.festival_backend.dto.category

data class CategoryRequest(
    val name: String,
    val slug: String,
    val description: String? = null,
    val thumbnailUrl: String? = null,
    val active: Boolean = true,
    val displayOrder: Int = 0
)