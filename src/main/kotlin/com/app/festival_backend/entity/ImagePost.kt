package com.app.festival_backend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "image_posts")
data class ImagePost(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, length = 255)
    val title: String,

    @Column(nullable = false, length = 500)
    val imageUrl: String,

    @Column(length = 500)
    val thumbnailUrl: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    val category: Category,

    @Column(nullable = false)
    val isPremium: Boolean = false,

    @Column(nullable = false)
    val active: Boolean = true,

    @Column(nullable = false)
    val displayOrder: Int = 0,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)