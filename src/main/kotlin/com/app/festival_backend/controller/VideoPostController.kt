package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.video.VideoPostRequest
import com.app.festival_backend.dto.video.VideoPostResponse
import com.app.festival_backend.service.VideoPostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class VideoPostController(
    private val videoPostService: VideoPostService
) {

    @PostMapping("/videos")
    fun create(@Valid @RequestBody request: VideoPostRequest): ResponseEntity<ApiResponse<VideoPostResponse>> {
        val response = videoPostService.create(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse(
                status = 201,
                message = "Video created successfully",
                data = response
            )
        )
    }

    @GetMapping("/videos")
    fun getAll(
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<ApiResponse<PagedResponse<VideoPostResponse>>> {
        val response = videoPostService.getAllPaginated(page)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Videos fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/videos/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<ApiResponse<VideoPostResponse>> {
        val response = videoPostService.getById(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video fetched successfully",
                data = response
            )
        )
    }

    @GetMapping("/categories/{categoryId}/videos")
    fun getByCategoryId(
        @PathVariable categoryId: Long,
        @RequestParam(defaultValue = "0") page: Int
    ): ResponseEntity<ApiResponse<PagedResponse<VideoPostResponse>>> {
        val response = videoPostService.getByCategoryIdPaginated(categoryId, page)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Videos fetched successfully",
                data = response
            )
        )
    }

    @PostMapping("/videos/{id}")
    fun update(
        @PathVariable id: Long,
        @Valid @RequestBody request: VideoPostRequest
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {
        val response = videoPostService.update(id, request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video updated successfully",
                data = response
            )
        )
    }

    @DeleteMapping("/videos/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<ApiResponse<Nothing>> {
        videoPostService.delete(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video deleted successfully",
                data = null
            )
        )
    }
}