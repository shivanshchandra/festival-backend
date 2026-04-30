package com.app.festival_backend.repository

import com.app.festival_backend.entity.ImagePost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ImagePostRepository : JpaRepository<ImagePost, Long> {

    fun findAllByOrderByDisplayOrderAscIdAsc(): List<ImagePost>

    fun findByCategory_IdOrderByDisplayOrderAscIdAsc(categoryId: Long): List<ImagePost>

    fun findByCategory_Id(categoryId: Long, pageable: Pageable): Page<ImagePost>

    @Query("select coalesce(max(i.displayOrder), 0) from ImagePost i")
    fun findMaxDisplayOrder(): Int

    @Query("""
    SELECT i FROM ImagePost i
    WHERE i.category.id = :categoryId
    AND LOWER(i.title) LIKE LOWER(CONCAT('%', :search, '%'))
""")
    fun searchByCategory(
        categoryId: Long,
        search: String,
        pageable: Pageable
    ): Page<ImagePost>

    @Query("""
    SELECT i FROM ImagePost i
    WHERE LOWER(i.title) LIKE LOWER(CONCAT('%', :search, '%'))
""")
    fun searchAll(
        search: String,
        pageable: Pageable
    ): Page<ImagePost>
}