package com.app.festival_backend.dto.video

import com.app.festival_backend.entity.VideoPost

data class VideoPostResponse(
    val id: Long,
    val title: String,
    val videoUrl: String,
    val thumbnailUrl: String?,
    val categoryId: Long,
    val categoryName: String,
    val isPremium: Boolean,
    val active: Boolean,
    val displayOrder: Int
) {
    companion object {
        fun from(entity: VideoPost): VideoPostResponse {
            return VideoPostResponse(
                id = entity.id,
                title = entity.title,
                videoUrl = entity.videoUrl,
                thumbnailUrl = entity.thumbnailUrl,
                categoryId = entity.category.id,
                categoryName = entity.category.name,
                isPremium = entity.isPremium,
                active = entity.active,
                displayOrder = entity.displayOrder
            )
        }
    }
}