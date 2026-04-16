package com.app.festival_backend.controller

import com.app.festival_backend.dto.common.ApiResponse
import com.app.festival_backend.dto.common.DeleteVideoRequest
import com.app.festival_backend.dto.common.PagedResponse
import com.app.festival_backend.dto.video.VideoPostRequest
import com.app.festival_backend.dto.video.VideoPostResponse
import com.app.festival_backend.dto.video.VideoPostUpdateRequest
import com.app.festival_backend.service.FileStorageService
import com.app.festival_backend.service.VideoPostService
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/videos")
class VideoPostController(
    private val videoPostService: VideoPostService,
    private val fileStorageService: FileStorageService
) {

    @PostMapping(value = ["/create"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createJson(
        @Valid @RequestBody request: VideoPostRequest
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {
        val response = videoPostService.create(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video created successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/create"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createMultipart(
        @RequestParam("title") title: String,
        @RequestParam("categoryId") categoryId: Long,
        @RequestParam("isPremium", required = false) isPremium: Boolean?,
        @RequestParam("displayOrder", required = false) displayOrder: Int?,
        @RequestParam("videoFile", required = false) videoFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("videoUrl", required = false) videoUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {

        val finalVideoUrl = if (videoFile != null && !videoFile.isEmpty) {
            fileStorageService.uploadVideo(videoFile).fileUrl
        } else {
            videoUrl.orEmpty()
        }

        val finalThumbnailUrl = if (thumbnailFile != null && !thumbnailFile.isEmpty) {
            fileStorageService.uploadImage(thumbnailFile).fileUrl
        } else {
            thumbnailUrl
        }

        val request = VideoPostRequest(
            title = title,
            videoUrl = finalVideoUrl,
            thumbnailUrl = finalThumbnailUrl,
            categoryId = categoryId,
            isPremium = isPremium ?: false,
            displayOrder = displayOrder
        )

        val response = videoPostService.create(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video created successfully",
                data = response
            )
        )
    }

    @GetMapping("/get")
    fun getAll(
        @RequestParam(defaultValue = "1") page: Int
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

    @GetMapping("/get/{id}")
    fun getById(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {
        val response = videoPostService.getById(id)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video fetched successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateJson(
        @Valid @RequestBody request: VideoPostUpdateRequest
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {
        val response = videoPostService.update(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video updated successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/update"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateMultipart(
        @RequestParam("videoId") videoId: Long,
        @RequestParam("title", required = false) title: String?,
        @RequestParam("categoryId", required = false) categoryId: Long?,
        @RequestParam("isPremium", required = false) isPremium: Boolean?,
        @RequestParam("displayOrder", required = false) displayOrder: Int?,
        @RequestParam("videoFile", required = false) videoFile: MultipartFile?,
        @RequestParam("thumbnailFile", required = false) thumbnailFile: MultipartFile?,
        @RequestParam("videoUrl", required = false) videoUrl: String?,
        @RequestParam("thumbnailUrl", required = false) thumbnailUrl: String?
    ): ResponseEntity<ApiResponse<VideoPostResponse>> {

        val finalVideoUrl = if (videoFile != null && !videoFile.isEmpty) {
            fileStorageService.uploadVideo(videoFile).fileUrl
        } else {
            videoUrl
        }

        val finalThumbnailUrl = if (thumbnailFile != null && !thumbnailFile.isEmpty) {
            fileStorageService.uploadImage(thumbnailFile).fileUrl
        } else {
            thumbnailUrl
        }

        val request = VideoPostUpdateRequest(
            videoId = videoId,
            title = title,
            videoUrl = finalVideoUrl,
            thumbnailUrl = finalThumbnailUrl,
            categoryId = categoryId,
            isPremium = isPremium,
            displayOrder = displayOrder
        )

        val response = videoPostService.update(request)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video updated successfully",
                data = response
            )
        )
    }

    @PostMapping(value = ["/delete"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteJson(
        @Valid @RequestBody request: DeleteVideoRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        videoPostService.delete(request.videoId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video deleted successfully",
                data = null
            )
        )
    }

    @PostMapping(value = ["/delete"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun deleteMultipart(
        @Valid @ModelAttribute request: DeleteVideoRequest
    ): ResponseEntity<ApiResponse<Nothing>> {
        videoPostService.delete(request.videoId)
        return ResponseEntity.ok(
            ApiResponse(
                status = 200,
                message = "Video deleted successfully",
                data = null
            )
        )
    }
}