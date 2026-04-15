package com.app.festival_backend.dto.image

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.URL

data class ImagePostUpdateRequest(

    @field:NotNull(message = "Image id is required")
    val imageId: Long,

    val title: String? = null,

    @field:URL(message = "Image URL must be a valid URL")
    val imageUrl: String? = null,

    @field:URL(message = "Thumbnail URL must be a valid URL")
    val thumbnailUrl: String? = null,

    val categoryId: Long? = null,
    val isPremium: Boolean? = null,

    @field:Min(value = 0, message = "Display order must be 0 or greater")
    val displayOrder: Int? = null
)