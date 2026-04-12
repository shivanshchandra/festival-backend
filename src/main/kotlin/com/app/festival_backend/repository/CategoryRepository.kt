package com.app.festival_backend.repository

import com.app.festival_backend.entity.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

    fun findBySlug(slug: String): Category?

    fun findByActiveTrueOrderByDisplayOrderAsc(): List<Category>

    fun findByActiveTrue(pageable: Pageable): Page<Category>

    fun existsBySlug(slug: String): Boolean
}