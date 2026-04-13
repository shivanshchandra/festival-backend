package com.app.festival_backend.service

import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.video.VideoPostRequest
import com.app.festival_backend.dto.video.VideoPostResponse
import com.app.festival_backend.entity.VideoPost
import com.app.festival_backend.exception.ResourceNotFoundException
import com.app.festival_backend.repository.CategoryRepository
import com.app.festival_backend.repository.VideoPostRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class VideoPostService(
    private val videoPostRepository: VideoPostRepository,
    private val categoryRepository: CategoryRepository
) {

    fun create(request: VideoPostRequest): VideoPostResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ResourceNotFoundException("Category not found with id: ${request.categoryId}") }

        val videoPost = VideoPost(
            title = request.title,
            videoUrl = request.videoUrl,
            thumbnailUrl = request.thumbnailUrl,
            category = category,
            isPremium = request.isPremium,
            displayOrder = request.displayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return VideoPostResponse.from(videoPostRepository.save(videoPost))
    }

    fun getAllPaginated(page: Int): PagedResponse<VideoPostResponse> {
        val pageable = PageRequest.of(
            page,
            10,
            Sort.by(
                Sort.Order.asc("displayOrder"),
                Sort.Order.asc("id")
            )
        )

        val result = videoPostRepository.findAll(pageable)

        return PagedResponse(
            content = result.content.map { VideoPostResponse.from(it) },
            page = result.number,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
    }

    fun getById(id: Long): VideoPostResponse {
        val video = videoPostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Video post not found with id: $id") }

        return VideoPostResponse.from(video)
    }

    fun getByCategoryIdPaginated(categoryId: Long, page: Int): PagedResponse<VideoPostResponse> {
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

        val result = videoPostRepository.findByCategory_Id(categoryId, pageable)

        return PagedResponse(
            content = result.content.map { VideoPostResponse.from(it) },
            page = result.number,
            size = result.size,
            totalElements = result.totalElements,
            totalPages = result.totalPages,
            last = result.isLast
        )
    }

    fun update(id: Long, request: VideoPostRequest): VideoPostResponse {
        val existing = videoPostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Video post not found with id: $id") }

        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ResourceNotFoundException("Category not found with id: ${request.categoryId}") }

        val updated = existing.copy(
            title = request.title,
            videoUrl = request.videoUrl,
            thumbnailUrl = request.thumbnailUrl,
            category = category,
            isPremium = request.isPremium,
            displayOrder = request.displayOrder,
            updatedAt = LocalDateTime.now()
        )

        return VideoPostResponse.from(videoPostRepository.save(updated))
    }

    fun delete(id: Long) {
        val existing = videoPostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Video post not found with id: $id") }

        videoPostRepository.delete(existing)
    }
}