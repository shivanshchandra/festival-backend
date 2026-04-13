package com.app.festival_backend.repository

import com.app.festival_backend.entity.QuotePost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QuotePostRepository : JpaRepository<QuotePost, Long> {

    fun findAllByOrderByDisplayOrderAscIdAsc(): List<QuotePost>

    fun findByCategory_IdOrderByDisplayOrderAscIdAsc(categoryId: Long): List<QuotePost>

    fun findByCategory_Id(categoryId: Long, pageable: Pageable): Page<QuotePost>
}