package com.app.festival_backend.dto.category

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.hibernate.validator.constraints.URL

data class CategoryRequest(

    @field:NotBlank(message = "Category name is required")
    @field:Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    val name: String,

    val description: String? = null,

    @field:URL(message = "Image URL must be a valid URL")
    val imageUrl: String? = null,

    @field:URL(message = "Thumbnail URL must be a valid URL")
    val thumbnailUrl: String? = null,

    val isPremium: Boolean = false,

    @field:Min(value = 0, message = "Display order must be 0 or greater")
    val displayOrder: Int = 0
)