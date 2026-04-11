package com.app.festival_backend.dto.video

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class VideoPostRequest(

    @field:NotBlank(message = "Video title is required")
    @field:Size(min = 2, max = 150, message = "Video title must be between 2 and 150 characters")
    val title: String,

    @field:NotBlank(message = "Video URL is required")
    val videoUrl: String,

    val thumbnailUrl: String? = null,

    @field:NotNull(message = "Category id is required")
    val categoryId: Long,

    val isPremium: Boolean = false,

    val active: Boolean = true,

    @field:Min(value = 0, message = "Display order must be 0 or greater")
    val displayOrder: Int = 0
)