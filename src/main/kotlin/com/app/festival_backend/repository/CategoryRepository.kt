package com.app.festival_backend.repository

import com.app.festival_backend.entity.Category
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {

    fun existsByName(name: String): Boolean

    @Query("select coalesce(max(c.displayOrder), 0) from Category c")
    fun findMaxDisplayOrder(): Int

    fun findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name: String, pageable: Pageable): Page<Category>
}