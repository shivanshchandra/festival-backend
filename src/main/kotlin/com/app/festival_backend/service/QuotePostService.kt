package com.app.festival_backend.service

import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.quote.QuotePostRequest
import com.app.festival_backend.dto.quote.QuotePostResponse
import com.app.festival_backend.dto.quote.QuotePostUpdateRequest
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

        val finalDisplayOrder = request.displayOrder ?: (quotePostRepository.findMaxDisplayOrder() + 1)

        val quotePost = QuotePost(
            title = request.title.trim(),
            quoteText = request.quoteText.trim(),
            category = category,
            isPremium = request.isPremium,
            displayOrder = finalDisplayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return QuotePostResponse.from(quotePostRepository.save(quotePost))
    }

    fun getAllPaginated(page: Int, size: Int): PagedResponse<QuotePostResponse> {
        val pageNumber = if (page < 1) 0 else page - 1
        val pageable = PageRequest.of(
            pageNumber,
            size,
            Sort.by(
                Sort.Order.asc("displayOrder"),
                Sort.Order.asc("id")
            )
        )

        val result = quotePostRepository.findAll(pageable)

        return PagedResponse(
            content = result.content.map { QuotePostResponse.from(it) },
            page = result.number + 1,
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

    fun getByCategoryIdPaginated(
        categoryId: Long,
        page: Int,
        size: Int,
        search: String?
    ): PagedResponse<QuotePostResponse> {

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
            quotePostRepository.findByCategory_IdAndTitleContainingIgnoreCaseOrCategory_IdAndQuoteTextContainingIgnoreCase(
                categoryId,
                search,
                pageable
            )
        } else {
            quotePostRepository.findByCategory_Id(categoryId, pageable)
        }

        return PagedResponse(
            content = result.content.map { QuotePostResponse.from(it) },
            page = result.number + 1,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
    }

    fun update(request: QuotePostUpdateRequest): QuotePostResponse {
        val existing = quotePostRepository.findById(request.quoteId)
            .orElseThrow { ResourceNotFoundException("Quote post not found with id: ${request.quoteId}") }

        val category = request.categoryId?.let {
            categoryRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Category not found with id: $it") }
        } ?: existing.category

        val updated = existing.copy(
            title = request.title?.takeIf { it.isNotBlank() }?.trim() ?: existing.title,
            quoteText = request.quoteText?.takeIf { it.isNotBlank() }?.trim() ?: existing.quoteText,
            category = category,
            isPremium = request.isPremium ?: existing.isPremium,
            displayOrder = request.displayOrder ?: existing.displayOrder,
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