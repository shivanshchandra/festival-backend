package com.app.festival_backend.dto.quote

data class QuotePostRequest(
    val title: String,
    val quoteText: String,
    val author: String? = null,
    val categoryId: Long,
    val isPremium: Boolean = false,
    val active: Boolean = true,
    val displayOrder: Int = 0
)