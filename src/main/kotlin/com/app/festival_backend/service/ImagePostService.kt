package com.app.festival_backend.service

import com.app.festival_backend.dto.image.ImagePostRequest
import com.app.festival_backend.dto.image.ImagePostResponse
import com.app.festival_backend.entity.ImagePost
import com.app.festival_backend.exception.ResourceNotFoundException
import com.app.festival_backend.repository.CategoryRepository
import com.app.festival_backend.repository.ImagePostRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ImagePostService(
    private val imagePostRepository: ImagePostRepository,
    private val categoryRepository: CategoryRepository
) {

    fun create(request: ImagePostRequest): ImagePostResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ResourceNotFoundException("Category not found with id: ${request.categoryId}") }

        val imagePost = ImagePost(
            title = request.title,
            imageUrl = request.imageUrl,
            thumbnailUrl = request.thumbnailUrl,
            category = category,
            isPremium = request.isPremium,
            active = request.active,
            displayOrder = request.displayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return ImagePostResponse.from(imagePostRepository.save(imagePost))
    }

    fun getAll(): List<ImagePostResponse> {
        return imagePostRepository.findByActiveTrueOrderByDisplayOrderAscIdAsc()
            .map { ImagePostResponse.from(it) }
    }

    fun getById(id: Long): ImagePostResponse {
        val imagePost = imagePostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Image post not found with id: $id") }

        return ImagePostResponse.from(imagePost)
    }

    fun getByCategoryId(categoryId: Long): List<ImagePostResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw ResourceNotFoundException("Category not found with id: $categoryId")
        }

        return imagePostRepository.findByCategoryIdAndActiveTrueOrderByDisplayOrderAscIdAsc(categoryId)
            .map { ImagePostResponse.from(it) }
    }

    fun update(id: Long, request: ImagePostRequest): ImagePostResponse {
        val existing = imagePostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Image post not found with id: $id") }

        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ResourceNotFoundException("Category not found with id: ${request.categoryId}") }

        val updated = existing.copy(
            title = request.title,
            imageUrl = request.imageUrl,
            thumbnailUrl = request.thumbnailUrl,
            category = category,
            isPremium = request.isPremium,
            active = request.active,
            displayOrder = request.displayOrder,
            updatedAt = LocalDateTime.now()
        )

        return ImagePostResponse.from(imagePostRepository.save(updated))
    }

    fun delete(id: Long) {
        val existing = imagePostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Image post not found with id: $id") }

        imagePostRepository.delete(existing)
    }
}