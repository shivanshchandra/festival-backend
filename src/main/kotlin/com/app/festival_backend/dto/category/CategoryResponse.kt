package com.app.festival_backend.dto.category

import com.app.festival_backend.entity.Category

data class CategoryResponse(
    val id: Long,
    val name: String,
    val slug: String,
    val description: String?,
    val thumbnailUrl: String?,
    val active: Boolean,
    val displayOrder: Int
) {
    companion object {
        fun from(entity: Category): CategoryResponse {
            return CategoryResponse(
                id = entity.id,
                name = entity.name,
                slug = entity.slug,
                description = entity.description,
                thumbnailUrl = entity.thumbnailUrl,
                active = entity.active,
                displayOrder = entity.displayOrder
            )
        }
    }
}