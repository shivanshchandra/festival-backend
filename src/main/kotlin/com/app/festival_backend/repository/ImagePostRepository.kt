package com.app.festival_backend.repository

import com.app.festival_backend.entity.ImagePost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImagePostRepository : JpaRepository<ImagePost, Long> {

    fun findByActiveTrueOrderByDisplayOrderAscIdAsc(): List<ImagePost>

    fun findByCategoryIdAndActiveTrueOrderByDisplayOrderAscIdAsc(categoryId: Long): List<ImagePost>

    fun findByActiveTrue(pageable: Pageable): Page<ImagePost>

    fun findByCategoryIdAndActiveTrue(categoryId: Long, pageable: Pageable): Page<ImagePost>
}