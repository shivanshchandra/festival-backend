package com.app.festival_backend.dto.category

import com.app.festival_backend.entity.Category

data class CategoryResponse(
    val id: Long,
    val name: String,
    val description: String?,
    val imageUrl: String?,
    val thumbnailUrl: String?,
    val isPremium: Boolean,
    val displayOrder: Int
) {
    companion object {
        fun from(entity: Category): CategoryResponse {
            return CategoryResponse(
                id = entity.id,
                name = entity.name,
                description = entity.description,
                imageUrl = entity.imageUrl,
                thumbnailUrl = entity.thumbnailUrl,
                isPremium = entity.isPremium,
                displayOrder = entity.displayOrder
            )
        }
    }
}