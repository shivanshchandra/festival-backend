package com.app.festival_backend.dto.common

import jakarta.validation.constraints.NotNull

data class CategoryContentRequest(
    @field:NotNull(message = "Category id is required")
    val categoryId: Long
)

data class DeleteCategoryRequest(
    @field:NotNull(message = "Category id is required")
    val categoryId: Long
)

data class DeleteImageRequest(
    @field:NotNull(message = "Image id is required")
    val imageId: Long
)

data class DeleteQuoteRequest(
    @field:NotNull(message = "Quote id is required")
    val quoteId: Long
)

data class DeleteVideoRequest(
    @field:NotNull(message = "Video id is required")
    val videoId: Long
)