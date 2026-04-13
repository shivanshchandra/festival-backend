package com.app.festival_backend.dto.quote

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class QuotePostRequest(

    @field:NotBlank(message = "Quote title is required")
    @field:Size(min = 2, max = 150, message = "Quote title must be between 2 and 150 characters")
    val title: String,

    @field:NotBlank(message = "Quote text is required")
    val quoteText: String,

    @field:NotNull(message = "Category id is required")
    val categoryId: Long,

    val isPremium: Boolean = false,

    @field:Min(value = 0, message = "Display order must be 0 or greater")
    val displayOrder: Int = 0
)