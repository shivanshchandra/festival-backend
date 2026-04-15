package com.app.festival_backend.repository

import com.app.festival_backend.entity.VideoPost
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface VideoPostRepository : JpaRepository<VideoPost, Long> {

    fun findAllByOrderByDisplayOrderAscIdAsc(): List<VideoPost>

    fun findByCategory_IdOrderByDisplayOrderAscIdAsc(categoryId: Long): List<VideoPost>

    fun findByCategory_Id(categoryId: Long, pageable: Pageable): Page<VideoPost>

    @Query("select coalesce(max(v.displayOrder), 0) from VideoPost v")
    fun findMaxDisplayOrder(): Int
}