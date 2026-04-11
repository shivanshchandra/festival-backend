package com.app.festival_backend.repository

import com.app.festival_backend.entity.QuotePost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuotePostRepository : JpaRepository<QuotePost, Long> {

    fun findByActiveTrueOrderByDisplayOrderAscIdAsc(): List<QuotePost>

    fun findByCategoryIdAndActiveTrueOrderByDisplayOrderAscIdAsc(categoryId: Long): List<QuotePost>
}