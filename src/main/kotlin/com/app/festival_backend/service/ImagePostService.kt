package com.app.festival_backend.service

import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.image.ImagePostRequest
import com.app.festival_backend.dto.image.ImagePostResponse
import com.app.festival_backend.dto.image.ImagePostUpdateRequest
import com.app.festival_backend.entity.ImagePost
import com.app.festival_backend.exception.BadRequestException
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

        val finalImageUrl = request.imageUrl.trim()
        if (finalImageUrl.isBlank()) {
            throw BadRequestException("Image file is required")
        }

        val finalDisplayOrder = request.displayOrder ?: (imagePostRepository.findMaxDisplayOrder() + 1)

        val imagePost = ImagePost(
            title = request.title.trim(),
            imageUrl = finalImageUrl,
            thumbnailUrl = request.thumbnailUrl?.takeIf { it.isNotBlank() },
            category = category,
            isPremium = request.isPremium,
            displayOrder = finalDisplayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return ImagePostResponse.from(imagePostRepository.save(imagePost))
    }

    fun getAllPaginated(page: Int, size: Int): PagedResponse<ImagePostResponse> {
        val pageNumber = if (page < 1) 0 else page - 1
        val pageable = PageRequest.of(
            pageNumber,
            size,
            Sort.by(
                Sort.Order.asc("displayOrder"),
                Sort.Order.asc("id")
            )
        )

        val result = imagePostRepository.findAll(pageable)

        return PagedResponse(
            content = result.content.map { ImagePostResponse.from(it) },
            page = result.number + 1,
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

    fun getByCategoryIdPaginated(
        categoryId: Long,
        page: Int,
        size: Int,
        search: String?
    ): PagedResponse<ImagePostResponse> {

        if (!categoryRepository.existsById(categoryId)) {
            throw ResourceNotFoundException("Category not found with id: $categoryId")
        }

        val pageNumber = if (page < 1) 0 else page - 1

        val pageable = PageRequest.of(
            pageNumber,
            size,
            Sort.by(
                Sort.Order.asc("displayOrder"),
                Sort.Order.asc("id")
            )
        )

        val result = if (!search.isNullOrBlank()) {
            imagePostRepository.findByCategory_IdAndTitleContainingIgnoreCase(categoryId, search, pageable)
        } else {
            imagePostRepository.findByCategory_Id(categoryId, pageable)
        }

        return PagedResponse(
            content = result.content.map { ImagePostResponse.from(it) },
            page = result.number + 1,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
    }

    fun update(request: ImagePostUpdateRequest): ImagePostResponse {
        val existing = imagePostRepository.findById(request.imageId)
            .orElseThrow { ResourceNotFoundException("Image post not found with id: ${request.imageId}") }

        val category = request.categoryId?.let {
            categoryRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Category not found with id: $it") }
        } ?: existing.category

        val updated = existing.copy(
            title = request.title?.takeIf { it.isNotBlank() } ?: existing.title,
            imageUrl = request.imageUrl?.takeIf { it.isNotBlank() } ?: existing.imageUrl,
            thumbnailUrl = request.thumbnailUrl?.takeIf { it.isNotBlank() } ?: existing.thumbnailUrl,
            category = category,
            isPremium = request.isPremium ?: existing.isPremium,
            displayOrder = request.displayOrder ?: existing.displayOrder,
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