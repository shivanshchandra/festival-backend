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
        if (categoryRepository.existsBySlug(request.slug)) {
            throw BadRequestException("Category slug already exists: ${request.slug}")
        }

        val category = Category(
            name = request.name,
            slug = request.slug,
            description = request.description,
            thumbnailUrl = request.thumbnailUrl,
            active = request.active,
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

        val result = categoryRepository.findByActiveTrue(pageable)

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

        val anotherCategory = categoryRepository.findBySlug(request.slug)
        if (anotherCategory != null && anotherCategory.id != id) {
            throw BadRequestException("Category slug already exists: ${request.slug}")
        }

        val updated = existing.copy(
            name = request.name,
            slug = request.slug,
            description = request.description,
            thumbnailUrl = request.thumbnailUrl,
            active = request.active,
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