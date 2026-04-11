package com.app.festival_backend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "categories")
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true, length = 100)
    val name: String,

    @Column(nullable = false, unique = true, length = 100)
    val slug: String,

    @Column(columnDefinition = "TEXT")
    val description: String? = null,

    @Column(length = 255)
    val thumbnailUrl: String? = null,

    @Column(nullable = false)
    val active: Boolean = true,

    @Column(nullable = false)
    val displayOrder: Int = 0,

    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)