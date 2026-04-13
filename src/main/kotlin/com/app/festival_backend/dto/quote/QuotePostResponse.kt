package com.app.festival_backend.dto.quote

import com.app.festival_backend.entity.QuotePost

data class QuotePostResponse(
    val id: Long,
    val title: String,
    val quoteText: String,
    val categoryId: Long,
    val categoryName: String,
    val isPremium: Boolean,
    val displayOrder: Int
) {
    companion object {
        fun from(entity: QuotePost): QuotePostResponse {
            return QuotePostResponse(
                id = entity.id,
                title = entity.title,
                quoteText = entity.quoteText,
                categoryId = entity.category.id,
                categoryName = entity.category.name,
                isPremium = entity.isPremium,
                displayOrder = entity.displayOrder
            )
        }
    }
}