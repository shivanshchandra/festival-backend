package com.app.festival_backend.dto.image

import com.app.festival_backend.entity.ImagePost

data class ImagePostResponse(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val thumbnailUrl: String?,
    val categoryId: Long,
    val categoryName: String,
    val isPremium: Boolean,
    val displayOrder: Int
) {
    companion object {
        fun from(entity: ImagePost): ImagePostResponse {
            return ImagePostResponse(
                id = entity.id,
                title = entity.title,
                imageUrl = entity.imageUrl,
                thumbnailUrl = entity.thumbnailUrl,
                categoryId = entity.category.id,
                categoryName = entity.category.name,
                isPremium = entity.isPremium,
                displayOrder = entity.displayOrder
            )
        }
    }
}