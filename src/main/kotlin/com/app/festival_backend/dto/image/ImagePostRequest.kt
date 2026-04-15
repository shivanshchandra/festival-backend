package com.app.festival_backend.dto.image

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL

data class ImagePostRequest(

    @field:NotBlank(message = "Image title is required")
    @field:Size(min = 2, max = 150, message = "Image title must be between 2 and 150 characters")
    val title: String,

    @field:NotBlank(message = "Image URL is required")
    @field:URL(message = "Image URL must be a valid URL")
    val imageUrl: String,

    @field:URL(message = "Thumbnail URL must be a valid URL")
    val thumbnailUrl: String? = null,

    @field:NotNull(message = "Category id is required")
    val categoryId: Long,

    val isPremium: Boolean = false,

    @field:Min(value = 0, message = "Display order must be 0 or greater")
    val displayOrder: Int? = null
)