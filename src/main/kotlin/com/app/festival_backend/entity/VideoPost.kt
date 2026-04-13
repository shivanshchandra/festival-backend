package com.app.festival_backend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "video_posts")
data class VideoPost(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 255)
    val title: String,

    @Column(nullable = false, length = 500)
    val videoUrl: String,

    @Column(length = 500)
    val thumbnailUrl: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @Column(nullable = false)
    val isPremium: Boolean = false,

    @Column(nullable = false)
    val displayOrder: Int = 0,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)