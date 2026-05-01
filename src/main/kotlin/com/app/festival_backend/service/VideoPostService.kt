package com.app.festival_backend.service

import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.video.VideoPostRequest
import com.app.festival_backend.dto.video.VideoPostResponse
import com.app.festival_backend.dto.video.VideoPostUpdateRequest
import com.app.festival_backend.entity.VideoPost
import com.app.festival_backend.exception.BadRequestException
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
    private val categoryRepository: CategoryRepository,
    private val fileStorageService: FileStorageService
) {

    fun create(request: VideoPostRequest): VideoPostResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { ResourceNotFoundException("Category not found with id: ${request.categoryId}") }

        val finalVideoUrl = request.videoUrl.trim()
        if (finalVideoUrl.isBlank()) {
            throw BadRequestException("Video file is required")
        }

        val finalDisplayOrder = request.displayOrder ?: (videoPostRepository.findMaxDisplayOrder() + 1)

        val videoPost = VideoPost(
            title = request.title.trim(),
            videoUrl = finalVideoUrl,
            thumbnailUrl = request.thumbnailUrl?.takeIf { it.isNotBlank() },
            category = category,
            isPremium = request.isPremium,
            displayOrder = finalDisplayOrder,
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

    fun getByCategoryIdPaginated(categoryId: Long, page: Int, size: Int): PagedResponse<VideoPostResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw ResourceNotFoundException("Category not found with id: $categoryId")
        }

        val pageable = PageRequest.of(
            page,
            size,
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

    fun update(request: VideoPostUpdateRequest): VideoPostResponse {
        val existing = videoPostRepository.findById(request.videoId)
            .orElseThrow { ResourceNotFoundException("Video post not found with id: ${request.videoId}") }

        val category = request.categoryId?.let {
            categoryRepository.findById(it)
                .orElseThrow { ResourceNotFoundException("Category not found with id: $it") }
        } ?: existing.category

        val updated = existing.copy(
            title = request.title?.takeIf { it.isNotBlank() } ?: existing.title,
            videoUrl = request.videoUrl?.takeIf { it.isNotBlank() } ?: existing.videoUrl,
            thumbnailUrl = request.thumbnailUrl?.takeIf { it.isNotBlank() } ?: existing.thumbnailUrl,
            category = category,
            isPremium = request.isPremium ?: existing.isPremium,
            displayOrder = request.displayOrder ?: existing.displayOrder,
            updatedAt = LocalDateTime.now()
        )

        return VideoPostResponse.from(videoPostRepository.save(updated))
    }

    fun delete(id: Long) {
        val existing = videoPostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Video post not found with id: $id") }

        fileStorageService.deleteFile(existing.videoUrl)
        fileStorageService.deleteFile(existing.thumbnailUrl)

        videoPostRepository.delete(existing)
    }
}