package com.app.festival_backend.service

import com.app.festival_backend.dto.quote.QuotePostRequest
import com.app.festival_backend.dto.quote.QuotePostResponse
import com.app.festival_backend.entity.QuotePost
import com.app.festival_backend.exception.ResourceNotFoundException
import com.app.festival_backend.repository.CategoryRepository
import com.app.festival_backend.repository.QuotePostRepository
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
            author = request.author,
            category = category,
            isPremium = request.isPremium,
            active = request.active,
            displayOrder = request.displayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return QuotePostResponse.from(quotePostRepository.save(quotePost))
    }

    fun getAll(): List<QuotePostResponse> {
        return quotePostRepository.findByActiveTrueOrderByDisplayOrderAscIdAsc()
            .map { QuotePostResponse.from(it) }
    }

    fun getById(id: Long): QuotePostResponse {
        val quotePost = quotePostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Quote post not found with id: $id") }

        return QuotePostResponse.from(quotePost)
    }

    fun getByCategoryId(categoryId: Long): List<QuotePostResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw ResourceNotFoundException("Category not found with id: $categoryId")
        }

        return quotePostRepository.findByCategoryIdAndActiveTrueOrderByDisplayOrderAscIdAsc(categoryId)
            .map { QuotePostResponse.from(it) }
    }

    fun update(id: Long, request: QuotePostRequest): QuotePostResponse {
        val existing = quotePostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Quote post not found with id: $id") }

        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ResourceNotFoundException("Category not found with id: ${request.categoryId}") }

        val updated = existing.copy(
            title = request.title,
            quoteText = request.quoteText,
            author = request.author,
            category = category,
            isPremium = request.isPremium,
            active = request.active,
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