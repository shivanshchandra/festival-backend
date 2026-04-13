package com.app.festival_backend.service

import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.image.ImagePostRequest
import com.app.festival_backend.dto.image.ImagePostResponse
import com.app.festival_backend.entity.ImagePost
import com.app.festival_backend.exception.ResourceNotFoundException
import com.app.festival_backend.repository.CategoryRepository
import com.app.festival_backend.repository.ImagePostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
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
            displayOrder = request.displayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return ImagePostResponse.from(imagePostRepository.save(imagePost))
    }

    fun getAllPaginated(page: Int): PagedResponse<ImagePostResponse> {
        val pageable = PageRequest.of(
            page,
            10,
            Sort.by(
                Sort.Order.asc("displayOrder"),
                Sort.Order.asc("id")
            )
        )

        val result = imagePostRepository.findAll(pageable)

        return PagedResponse(
            content = result.content.map { ImagePostResponse.from(it) },
            page = result.number,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
    }

    fun getById(id: Long): ImagePostResponse {
        val imagePost = imagePostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Image post not found with id: $id") }

        return ImagePostResponse.from(imagePost)
    }

    fun getByCategoryIdPaginated(categoryId: Long, page: Int): PagedResponse<ImagePostResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw ResourceNotFoundException("Category not found with id: $categoryId")
        }

        val pageable = PageRequest.of(
            page,
            10,
            Sort.by(
                Sort.Order.asc("displayOrder"),
                Sort.Order.asc("id")
            )
        )

        val result = imagePostRepository.findByCategory_Id(categoryId, pageable)

        return PagedResponse(
            content = result.content.map { ImagePostResponse.from(it) },
            page = result.number,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
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