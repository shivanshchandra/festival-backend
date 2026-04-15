package com.app.festival_backend.dto.category

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.URL

data class CategoryUpdateRequest(

    @field:NotNull(message = "Category id is required")
    val categoryId: Long,

    val name: String? = null,
    val description: String? = null,

    @field:URL(message = "Image URL must be a valid URL")
    val imageUrl: String? = null,

    @field:URL(message = "Thumbnail URL must be a valid URL")
    val thumbnailUrl: String? = null,

    val isPremium: Boolean? = null,

    @field:Min(value = 0, message = "Display order must be 0 or greater")
    val displayOrder: Int? = null
)