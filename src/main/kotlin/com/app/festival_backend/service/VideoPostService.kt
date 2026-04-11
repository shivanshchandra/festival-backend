package com.app.festival_backend.service

import com.app.festival_backend.dto.video.VideoPostRequest
import com.app.festival_backend.dto.video.VideoPostResponse
import com.app.festival_backend.entity.VideoPost
import com.app.festival_backend.exception.ResourceNotFoundException
import com.app.festival_backend.repository.CategoryRepository
import com.app.festival_backend.repository.VideoPostRepository
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
            active = request.active,
            displayOrder = request.displayOrder,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        return VideoPostResponse.from(videoPostRepository.save(videoPost))
    }

    fun getAll(): List<VideoPostResponse> {
        return videoPostRepository.findByActiveTrueOrderByDisplayOrderAscIdAsc()
            .map { VideoPostResponse.from(it) }
    }

    fun getById(id: Long): VideoPostResponse {
        val video = videoPostRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Video post not found with id: $id") }

        return VideoPostResponse.from(video)
    }

    fun getByCategoryId(categoryId: Long): List<VideoPostResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw ResourceNotFoundException("Category not found with id: $categoryId")
        }

        return videoPostRepository.findByCategoryIdAndActiveTrueOrderByDisplayOrderAscIdAsc(categoryId)
            .map { VideoPostResponse.from(it) }
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
            active = request.active,
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