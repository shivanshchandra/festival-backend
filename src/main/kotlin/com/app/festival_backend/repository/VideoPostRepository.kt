package com.app.festival_backend.repository

import com.app.festival_backend.entity.VideoPost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VideoPostRepository : JpaRepository<VideoPost, Long> {

    fun findByActiveTrueOrderByDisplayOrderAscIdAsc(): List<VideoPost>

    fun findByCategoryIdAndActiveTrueOrderByDisplayOrderAscIdAsc(categoryId: Long): List<VideoPost>
}