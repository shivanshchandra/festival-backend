package com.app.festival_backend.dto.quote

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class QuotePostUpdateRequest(

    @field:NotNull(message = "Quote id is required")
    val quoteId: Long,

    val title: String? = null,
    val quoteText: String? = null,
    val categoryId: Long? = null,
    val isPremium: Boolean? = null,

    @field:Min(value = 0, message = "Display order must be 0 or greater")
    val displayOrder: Int? = null
)