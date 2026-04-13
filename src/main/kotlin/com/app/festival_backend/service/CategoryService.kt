package com.app.festival_backend.service

import com.app.festival_backend.dto.category.CategoryRequest
import com.app.festival_backend.dto.category.CategoryResponse
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

        val category = Category(
            name = normalizedName,
            description = request.description,
            imageUrl = request.imageUrl,
            thumbnailUrl = request.thumbnailUrl,
            isPremium = request.isPremium,
            displayOrder = request.displayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return CategoryResponse.from(categoryRepository.save(category))
    }

    fun getAllPaginated(page: Int): PagedResponse<CategoryResponse> {
        val pageable = PageRequest.of(
            page,
            10,
            Sort.by(
                Sort.Order.asc("displayOrder"),
                Sort.Order.asc("id")
            )
        )

        val result = categoryRepository.findAll(pageable)

        return PagedResponse(
            content = result.content.map { CategoryResponse.from(it) },
            page = result.number,
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

    fun update(id: Long, request: CategoryRequest): CategoryResponse {
        val existing = categoryRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Category not found with id: $id") }

        val normalizedName = request.name.trim()

        if (normalizedName != existing.name && categoryRepository.existsByName(normalizedName)) {
            throw BadRequestException("Category already exists with name: $normalizedName")
        }

        val updated = existing.copy(
            name = normalizedName,
            description = request.description,
            imageUrl = request.imageUrl,
            thumbnailUrl = request.thumbnailUrl,
            isPremium = request.isPremium,
            displayOrder = request.displayOrder,
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