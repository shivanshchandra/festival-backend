package com.app.festival_backend.service

import com.app.festival_backend.dto.category.CategoryRequest
import com.app.festival_backend.dto.category.CategoryResponse
import com.app.festival_backend.dto.category.CategoryUpdateRequest
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.entity.Category
import com.app.festival_backend.exception.BadRequestException
import com.app.festival_backend.exception.ResourceNotFoundException
import com.app.festival_backend.repository.CategoryRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun create(request: CategoryRequest): CategoryResponse {
        val normalizedName = request.name.trim()

        if (categoryRepository.existsByName(normalizedName)) {
            throw BadRequestException("Category already exists with name: $normalizedName")
        }

        val finalImageUrl = request.imageUrl?.trim()
        val finalThumbnailUrl = request.thumbnailUrl?.trim()

        if (finalImageUrl.isNullOrBlank()) {
            throw BadRequestException("Image file is required")
        }

        val finalDisplayOrder =
            request.displayOrder ?: (categoryRepository.findMaxDisplayOrder() + 1)

        val category = Category(
            name = normalizedName,
            description = request.description?.trim(),
            imageUrl = finalImageUrl,
            thumbnailUrl = finalThumbnailUrl,
            isPremium = request.isPremium,
            displayOrder = finalDisplayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return CategoryResponse.from(categoryRepository.save(category))
    }

    fun getAllPaginated(page: Int, size: Int, search: String?): PagedResponse<CategoryResponse> {
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
            categoryRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(search, pageable)
        } else {
            categoryRepository.findAll(pageable)
        }

        return PagedResponse(
            content = result.content.map { CategoryResponse.from(it) },
            page = result.number + 1,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
    }

    fun getById(id: Long): CategoryResponse {
        val category = categoryRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Category not found with id: $id") }

        return CategoryResponse.from(category)
    }

    fun update(request: CategoryUpdateRequest): CategoryResponse {
        val existing = categoryRepository.findById(request.categoryId)
            .orElseThrow { ResourceNotFoundException("Category not found with id: ${request.categoryId}") }

        val finalName = request.name?.trim()?.takeIf { it.isNotBlank() } ?: existing.name

        if (finalName != existing.name && categoryRepository.existsByName(finalName)) {
            throw BadRequestException("Category already exists with name: $finalName")
        }

        val updated = existing.copy(
            name = finalName,
            description = request.description ?: existing.description,
            imageUrl = request.imageUrl?.takeIf { it.isNotBlank() } ?: existing.imageUrl,
            thumbnailUrl = request.thumbnailUrl?.takeIf { it.isNotBlank() } ?: existing.thumbnailUrl,
            isPremium = request.isPremium ?: existing.isPremium,
            displayOrder = request.displayOrder ?: existing.displayOrder,
            updatedAt = LocalDateTime.now()
        )

        return CategoryResponse.from(categoryRepository.save(updated))
    }

    fun delete(id: Long) {
        val existing = categoryRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Category not found with id: $id") }

        categoryRepository.delete(existing)
    }
}