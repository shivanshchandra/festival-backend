package com.app.festival_backend.dto.category

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CategoryRequest(

    @field:NotBlank(message = "Category name is required")
    @field:Size(min = 2, max = 100, message = "Category name must be between 2 and 100 characters")
    val name: String,

    @field:NotBlank(message = "Category slug is required")
    @field:Size(min = 2, max = 100, message = "Category slug must be between 2 and 100 characters")
    val slug: String,

    val description: String? = null,

    val thumbnailUrl: String? = null,

    val active: Boolean = true,

    @field:Min(value = 0, message = "Display order must be 0 or greater")
    val displayOrder: Int = 0
)