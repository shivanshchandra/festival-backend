package com.app.festival_backend.service

import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.quote.QuotePostRequest
import com.app.festival_backend.dto.quote.QuotePostResponse
import com.app.festival_backend.entity.QuotePost
import com.app.festival_backend.exception.ResourceNotFoundException
import com.app.festival_backend.repository.CategoryRepository
import com.app.festival_backend.repository.QuotePostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class QuotePostService(
    private val quotePostRepository: QuotePostRepository,
    private val categoryRepository: CategoryRepository
) {

    fun create(request: QuotePostRequest): QuotePostResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ResourceNotFoundException("Category not found with id: ${request.categoryId}") }

        val quotePost = QuotePost(
            title = request.title,
            quoteText = request.quoteText,
            category = category,
            isPremium = request.isPremium,
            displayOrder = request.displayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return QuotePostResponse.from(quotePostRepository.save(quotePost))
    }

    fun getAllPaginated(page: Int): PagedResponse<QuotePostResponse> {
        val pageable = PageRequest.of(
            page,
            10,
            Sort.by(
                Sort.Order.asc("displayOrder"),
                Sort.Order.asc("id")
            )
        )

        val result = quotePostRepository.findAll(pageable)

        return PagedResponse(
            content = result.content.map { QuotePostResponse.from(it) },
            page = result.number,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
    }

    fun getById(id: Long): QuotePostResponse {
        val quotePost = quotePostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Quote post not found with id: $id") }

        return QuotePostResponse.from(quotePost)
    }

    fun getByCategoryIdPaginated(categoryId: Long, page: Int): PagedResponse<QuotePostResponse> {
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

        val result = quotePostRepository.findByCategory_Id(categoryId, pageable)

        return PagedResponse(
            content = result.content.map { QuotePostResponse.from(it) },
            page = result.number,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
    }

    fun update(id: Long, request: QuotePostRequest): QuotePostResponse {
        val existing = quotePostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Quote post not found with id: $id") }

        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ResourceNotFoundException("Category not found with id: ${request.categoryId}") }

        val updated = existing.copy(
            title = request.title,
            quoteText = request.quoteText,
            category = category,
            isPremium = request.isPremium,
            displayOrder = request.displayOrder,
            updatedAt = LocalDateTime.now()
        )

        return QuotePostResponse.from(quotePostRepository.save(updated))
    }

    fun delete(id: Long) {
        val existing = quotePostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Quote post not found with id: $id") }

        quotePostRepository.delete(existing)
    }
}